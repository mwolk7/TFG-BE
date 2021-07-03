package tools.mtsuite.core.endpoint.testRunner.dto;

import tools.mtsuite.core.core.dto.GenericObjectDto;


public class ProjectVersionDto extends GenericObjectDto {
	/****************
	 * Relationships**
	 *****************/

	/****************
	 * Attributes **
	 *****************/
	private String project_version;

	private Long projectID;
	/****************
	 * Constructors **
	 *****************/
	public ProjectVersionDto() {
	}

	/****************
	 * getter & setter **
	 *****************/
	public Long getProjectID() {
		return projectID;
	}

	public void setProjectID(Long projectID) {
		this.projectID = projectID;
	}

	public String getProject_version() {
		return project_version;
	}

	public void setProject_version(String project_version) {
		this.project_version = project_version;
	}
}

