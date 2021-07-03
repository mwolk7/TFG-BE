package tools.mtsuite.core.endpoint.profile;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import tools.mtsuite.core.core.dto.ResponseStatus;
import tools.mtsuite.core.endpoint.profile.dto.CurrentUserDto;
import tools.mtsuite.core.endpoint.profile.dto.UserDto;

@RestController
@Tag(name = "profile")
@RequestMapping("/profile")
public class ProfileController {

	@Autowired
	private ProfileService profileService;

	@GetMapping("/currentUser")
	public CurrentUserDto getCurrentUser() {
		return profileService.getCurrentUser();
	}



	@PostMapping("/")
	public ResponseStatus saveOrUpdateProfile(@RequestBody UserDto userDto){
		if(userDto.getId() == null){
			return profileService.createUser(userDto);
		}else{
			return profileService.updateUser(userDto);
		}
	}


	@PutMapping("{id}/email/verified")
	public ResponseStatus verifyUserEmail(@PathVariable Long id,
										  @RequestParam(value = "value", required = true) Boolean value){
		return profileService.verifyUserEmail(id,value);
	}

	@PutMapping("{id}/enable")
	public ResponseStatus enableOrDisableUser(@PathVariable Long id,
										  @RequestParam(value = "value", required = true) Boolean value){
		return profileService.enableOrDisableUser(id,value);
	}

}
