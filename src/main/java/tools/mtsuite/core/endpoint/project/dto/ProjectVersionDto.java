package tools.mtsuite.core.endpoint.project.dto;
import tools.mtsuite.core.core.dto.GenericObjectDto;


public class ProjectVersionDto extends GenericObjectDto {

	private Long projectID;

	private String project_version;

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
