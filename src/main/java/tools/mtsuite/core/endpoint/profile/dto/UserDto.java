package tools.mtsuite.core.endpoint.profile.dto;
import tools.mtsuite.core.core.dto.GenericObjectDto;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class UserDto extends GenericObjectDto {
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

	@NotNull(message = "user must have a valid password")
	@Size(min = 8)
	private String password;

	@NotNull(message = "user must have a valid email")
	@Email
	private String email;
	private Boolean enable;
	private Boolean emailVerified;

	private String fullName;

	/****************
	 * getter&setter **
	 *****************/



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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public Boolean getEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(Boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getFullName() {
		return (name+" "+surname).trim();
	}

}
