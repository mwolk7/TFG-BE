package tools.mtsuite.core.endpoint.profile.dto;
import tools.mtsuite.core.common.model.User;
import tools.mtsuite.core.core.dto.GenericObjectDto;
import tools.mtsuite.core.core.dto.LoginUserDto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class CurrentUserDto extends GenericObjectDto {
	/****************
	 * Relationships**
	 *****************/

	@NotNull(message = "user must have a valid username")
	@Size(min = 4, max = 30)
	private String username;

	@NotNull(message = "user must have a valid name")
	@Size(min = 1)
	private String name;
	private String surname;

	private String fullname;

	@NotNull(message = "user must have a valid email")
	@Email
	private String email;
	private Boolean emailVerified;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(Boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getFullname() {
		return (this.name +" "+this.surname).trim();
	}

}
