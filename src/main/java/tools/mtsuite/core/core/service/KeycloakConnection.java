package tools.mtsuite.core.core.service;


import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tools.mtsuite.core.common.dao.IUserDao;
import tools.mtsuite.core.common.model.User;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
public class KeycloakConnection {

    @Value("${keycloak.protocol}://${keycloak.url}:${keycloak.port}/auth/")
    private String serverUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.clientId}")
    private String clientId;

    @Value("${keycloak.username}")
    private String username;

    @Value("${keycloak.password}")
    private String password;

    @Value("${keycloak.clientSecret}")
    private String clientSecret;

    @Autowired
    private RestTemplate httpClient;

    @Autowired
    private IUserDao userDao;

    private Keycloak kc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Log
    private static final Logger log = LoggerFactory.getLogger(KeycloakConnection.class);

    public KeycloakConnection() {
    }

   /**
    * Save or update user in keycloak
    * User attribute used - > username,password and name. (email = username)
    **/
    public Boolean saveOrUpdateKeycloakUser(User user, String password){
        UserRepresentation newKcUser = new UserRepresentation();

        newKcUser.setEnabled(user.getEnable());
        newKcUser.setUsername(user.getUsername());
        newKcUser.setFirstName(user.getName());
        newKcUser.setEmail(user.getEmail());
        newKcUser.setEmailVerified(user.getEmailVerified());

        Keycloak kcInstance = this.getKeycloakInstance();

        UsersResource usersResource = kcInstance.realm(realm).users();

        List<UserRepresentation> userRepresentationsList = usersResource.search(user.getUsername());

        if(userRepresentationsList.isEmpty()) {
            // User was not founded in keycloak -> create it
            Response result = usersResource.create(newKcUser);

            if (result.getStatus() != 201)
                throw new BadRequestException("Error creating user in keycloak");

            String locationHeader = result.getHeaderString("Location");
            String userId = locationHeader.replaceAll(".*/(.*)$", "$1");
            this.resetPasswordKeyCloak(usersResource, userId, password);
            user.setKeycloakKey(userId);
        }else{
            // The user already exist on keycloak -> update user
            newKcUser.setId(userRepresentationsList.get(0).getId());
            updateUser(newKcUser);
        }
        return true;
    }

    private void updateUser(UserRepresentation kcUser){
        Keycloak kcInstance = this.getKeycloakInstance();
        UsersResource usersResource = kcInstance.realm(realm).users();

        UserResource userResource = usersResource.get(kcUser.getId());

        List<UserRepresentation> userRepresentationsList = usersResource.search(kcUser.getUsername());
        if(!userRepresentationsList.isEmpty()) {
            userResource.update(kcUser);
        }
    }

    public void resetPassword(User user, String password){
        Keycloak kcInstance = this.getKeycloakInstance();
        UsersResource usersResource = kcInstance.realm(realm).users();
        this.resetPasswordKeyCloak(usersResource, user.getKeycloakKey(), password);
    }

    public void deleteUser(User user){
        if (user.getKeycloakKey() == null)
            throw new BadRequestException("User has not keycloak key");
        Keycloak kcInstance = this.getKeycloakInstance();
        UsersResource usersResource = kcInstance.realm(realm).users();
        UserResource userResource = usersResource.get(user.getKeycloakKey());
        userResource.remove();
    }

    private void resetPasswordKeyCloak(UsersResource usersResource, String userId, String password){
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        credential.setTemporary(false);
        UserResource userResource = usersResource.get(userId);
        userResource.resetPassword(credential);
    }


    private String getToken() {
        if(kc == null)
        {
            //kc = Keycloak.getInstance(serverUrl,  realm,  clientId,  authToken);
            log.info("Create instance");
            kc = this.getKeycloakInstance();
        }

        // TODO: just for test
        if(kc.isClosed()) {
            log.info("Is close");
            kc = this.getKeycloakInstance();
        }

        return kc.tokenManager().getAccessTokenString();
    }

    private Keycloak getKeycloakInstance() {
        return Keycloak.getInstance( this.serverUrl,  this.realm,  this.username,  this.password,  this.clientId,  this.clientSecret);
    }

}
