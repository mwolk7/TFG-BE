package tools.mtsuite.core.endpoint.profile;

import org.checkerframework.checker.units.qual.Current;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tools.mtsuite.core.common.dao.IUserDao;
import tools.mtsuite.core.common.model.User;
import tools.mtsuite.core.core.context.Mapeador;
import tools.mtsuite.core.core.excepctions.BadRequestException;
import tools.mtsuite.core.core.service.*;
import tools.mtsuite.core.endpoint.profile.dto.CurrentUserDto;
import tools.mtsuite.core.core.dto.*;
import tools.mtsuite.core.endpoint.profile.dto.UserDto;

import javax.transaction.Transactional;

@Service
@Transactional
public class ProfileService {

	@Autowired
	private Mapeador mapeador;

	@Autowired
	private CommonService commonService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private KeycloakConnection keycloakConnection;

	@Autowired
	private IUserDao userDao;


	/**
	 * This parameters is used to know if keycloak is active
	 * true  -> authentication without keycloak
	 * false -> authentication within keycloak -> user synchronization
	 **/
	@Value("${app.security.mode}")
	private String appSecurityDev;

	/**
	 * Get current user from the application context
	 * @return
	 */
	public CurrentUserDto getCurrentUser() {
		User user = commonService.getCurrentUser();
		CurrentUserDto currentUserDto = (CurrentUserDto) mapeador.map(user, CurrentUserDto.class);
		return currentUserDto;
	}

	/**
	 * Update user.
	 * Updatable fields -> name and password.
	 * The user will be updated in keycloak and in the database.
	 **/
	public ResponseStatus updateUser(UserDto userDto) {
		User user = getUser(userDto.getId());

		user.updateUser(userDto);
		if(appSecurityDev.equals("auth-kc-prod"))
			keycloakConnection.saveOrUpdateKeycloakUser(user, user.getPassword());

		if(userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
			// update password in the database
			user.setPassword(passwordEncoder.encode(userDto.getPassword()));

			// update password and user in keycloak
			if(appSecurityDev.equals("auth-kc-prod"))
				keycloakConnection.resetPassword(user, user.getPassword());
		}

		userDao.save(user);
		return new ResponseStatus("200","The user "+user.getUsername()+" was updated successfully");
	}

	/**
	 * Create user.
	 * Updatable fields -> name and password.
	 * The user will be created in keycloak and in the database.
	 **/
	public ResponseStatus createUser(UserDto userDto) {
		User user = new User(userDto);

		// create user in keycloak
		if(appSecurityDev.equals("auth-kc-prod"))
			keycloakConnection.saveOrUpdateKeycloakUser(user, userDto.getPassword());

		// set encoded password in the database
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));

		userDao.save(user);
		return new ResponseStatus("200","The user "+user.getUsername()+" was created successfully");
	}

	/**
	 * Update email verified attribute
	 * The user will be updated in keycloak and in the database.
	 **/
	public ResponseStatus verifyUserEmail(Long id, Boolean value) {
		User user = getUser(id);

		if(Boolean.TRUE.equals(user.getEmailVerified()))
			return new ResponseStatus("200"," The email is already validated.");

		user.setEmailVerified(value);

		if(appSecurityDev.equals("auth-kc-prod"))
			keycloakConnection.saveOrUpdateKeycloakUser(user, user.getPassword());

		userDao.save(user);
		return new ResponseStatus("200","The email from the user "+user.getUsername()+" was updated successfully");
	}

	/**
	 * Update email verified attribute
	 * The user will be updated in keycloak and in the database.
	 **/
	public ResponseStatus enableOrDisableUser(Long id, Boolean value) {
		User user = getUser(id);

		user.setEnable(value);

		if(appSecurityDev.equals("auth-kc-prod"))
			keycloakConnection.saveOrUpdateKeycloakUser(user, user.getPassword());

		userDao.save(user);
		return new ResponseStatus("200","The current user "+user.getUsername()+" status is: "+value.toString());
	}


	/**
	 * Private function to get the user from the database
	 **/
	private User getUser(Long id){
		if(id == null)
			throw new BadRequestException("500","The id is mandatory to search.");

		if(!userDao.existsById(id))
			throw new BadRequestException("500","User with id "+id+" not found");

		return userDao.findById(id).get();
	}


}
