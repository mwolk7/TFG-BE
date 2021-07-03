package tools.mtsuite.core.endpoint.project.dto;
import tools.mtsuite.core.common.model.enums.Roles;
import tools.mtsuite.core.core.dto.GenericObjectDto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


public class ProjectDto extends GenericObjectDto {

	/****************
	 * Relationships**
	 *****************/

	private Integer childProjectsCount ;
	private Long parentProjectId;
	private String parentProjectName;
	private Roles actualUserRole;

	private String projectPath;
	private List<ProjectDto> parentProjects;
	private List<ProjectDto> childNestedProjects;

	/****************
	 * Attributes **
	 *****************/

	@NotNull(message = "project must have a valid name")
	@Size(min = 1, max = 20)
	private String name;
	private String tag;

	private Boolean readOnly;

	/****************
	 * Getter&Setters **
	 *****************/

	public Integer getChildProjectsCount() {
		return childProjectsCount;
	}

	public void setChildProjectsCount(Integer childProjectsCount) {
		this.childProjectsCount = childProjectsCount;
	}

	public Long getParentProjectId() {
		return parentProjectId;
	}

	public void setParentProjectId(Long parentProjectId) {
		this.parentProjectId = parentProjectId;
	}

	public String getParentProjectName() {
		return parentProjectName;
	}

	public void setParentProjectName(String parentProjectName) {
		this.parentProjectName = parentProjectName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Roles getActualUserRole() {
		return actualUserRole;
	}

	public void setActualUserRole(Roles actualUserRole) {
		this.actualUserRole = actualUserRole;
	}

	public List<ProjectDto> getParentProjects() {
		return parentProjects;
	}

	public void setParentProjects(List<ProjectDto> parentProjects) {
		this.parentProjects = parentProjects;
	}

	public String getProjectPath() {
		return projectPath;
	}

	public void setProjectPath(String projectPath) {
		this.projectPath = projectPath;
	}

	public List<ProjectDto> getChildNestedProjects() {
		return childNestedProjects;
	}

	public void setChildNestedProjects(List<ProjectDto> childNestedProjects) {
		this.childNestedProjects = childNestedProjects;
	}

	public Boolean getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}
}
