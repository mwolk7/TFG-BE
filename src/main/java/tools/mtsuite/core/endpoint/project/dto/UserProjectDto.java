package tools.mtsuite.core.endpoint.project.dto;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import tools.mtsuite.core.common.model.Project;
import tools.mtsuite.core.common.model.User;
import tools.mtsuite.core.common.model.enums.Roles;
import tools.mtsuite.core.core.dto.GenericObjectDto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class UserProjectDto extends GenericObjectDto {

	@NotNull(message = "user must have a valid ID")
	private Long userId;


	private Roles rol;

	@NotNull(message = "project must have a valid ID")
	private Long projectId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Roles getRol() {
		return rol;
	}

	public void setRol(Roles rol) {
		this.rol = rol;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
}
