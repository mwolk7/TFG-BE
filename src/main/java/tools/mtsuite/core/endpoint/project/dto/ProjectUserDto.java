package tools.mtsuite.core.endpoint.project.dto;
import tools.mtsuite.core.common.model.UserProject;
import tools.mtsuite.core.common.model.enums.Roles;
import tools.mtsuite.core.core.dto.GenericObjectDto;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


public class ProjectUserDto extends GenericObjectDto {

	/* Extras */
	private Boolean isActualUserProject = false;

	private Roles role;
	/* PARAMS */

	@NotNull(message = "project user must have a valid name")
	@Size(min = 1, max = 30)
	private String name;

	@NotNull(message = "project user must have a valid username")
	@Size(min = 1, max = 30)
	private String username;

	private Long userId;

	@NotNull(message = "project user must have a valid email")
	@Email
	private String email;


	public ProjectUserDto() {
	}

	public ProjectUserDto(UserProject userProject) {
		this.setId(userProject.getId());
		this.name = userProject.getUser().getName();
		this.username = userProject.getUser().getUsername();
		this.email = userProject.getUser().getEmail();
		this.role = userProject.getRol();
		this.userId = userProject.getUser().getId();
	}

	public Boolean getActualUserProject() {
		return isActualUserProject;
	}

	public void setActualUserProject(Boolean actualUserProject) {
		isActualUserProject = actualUserProject;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Roles getRole() {
		return role;
	}

	public void setRole(Roles role) {
		this.role = role;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
