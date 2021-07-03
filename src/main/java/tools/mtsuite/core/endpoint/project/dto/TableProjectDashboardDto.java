package tools.mtsuite.core.endpoint.project.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import tools.mtsuite.core.common.model.enums.Roles;
import tools.mtsuite.core.core.dto.GenericObjectDto;

import java.util.Date;
import java.util.List;


public class TableProjectDashboardDto {


	/****************
	 * Attributes **
	 *****************/
	private Long id;
	private Long version;


	private String name;
	private String description;

	@JsonProperty("createdDate")
	private String created_Date;

	@JsonProperty("lastModifiedDate")
	private String last_Modified_Date;

	@JsonProperty("parentProjectId")
	private String parent_Project_Id;

	@JsonProperty("actualUserRole")
	private String actual_User_Role;

	private String projectPath;



	/****************
	 * Getter&Setters **
	 *****************/


	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public void setCreated_Date(String created_Date) {
		this.created_Date = created_Date;
	}

	public void setLast_Modified_Date(String last_Modified_Date) {
		this.last_Modified_Date = last_Modified_Date;
	}

	public void setParent_Project_Id(String parent_Project_Id) {
		this.parent_Project_Id = parent_Project_Id;
	}

	public void setActual_User_Role(String actual_User_Role) {
		this.actual_User_Role = actual_User_Role;
	}

	public Long getId() {
		return id;
	}

	public Long getVersion() {
		return version;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getCreated_Date() {
		return created_Date;
	}

	public String getLast_Modified_Date() {
		return last_Modified_Date;
	}

	public String getParent_Project_Id() {
		return parent_Project_Id;
	}

	public String getActual_User_Role() {
		return actual_User_Role;
	}

	public String getProjectPath() {
		return projectPath;
	}

	public void setProjectPath(String projectPath) {
		this.projectPath = projectPath;
	}
}
