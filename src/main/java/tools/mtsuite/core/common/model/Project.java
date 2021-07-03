package tools.mtsuite.core.common.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Formula;
import tools.mtsuite.core.common.model.enums.Roles;
import tools.mtsuite.core.core.utils.AppConstants;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


@Entity
@Table(name = AppConstants.PROJECTS)
public class Project extends GenericObject {
	/****************
	 * Relationships**
	 *****************/
	@OneToMany(mappedBy = "parentProject", fetch = FetchType.LAZY)
	@Cascade({ CascadeType.SAVE_UPDATE })
	private List<Project> childProjects = new ArrayList();

	@OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
	@Cascade({ CascadeType.SAVE_UPDATE })
	private List<FileTokenAccess> fileTokenAccesses = new ArrayList();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_PROJECT_ID")
	@Cascade({CascadeType.SAVE_UPDATE})
	private Project parentProject;


	@OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
	@Cascade({ CascadeType.SAVE_UPDATE })
	private List<UserProject> userProjects = new ArrayList();

	@OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
	@Cascade({ CascadeType.SAVE_UPDATE })
	private List<File> files = new ArrayList();

	@OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
	@Cascade({ CascadeType.SAVE_UPDATE })
	private List<TestSuite> testSuites = new ArrayList();


	@OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
	@Cascade({ CascadeType.SAVE_UPDATE })
	private List<ProjectVersion> projectVersions = new ArrayList();


	/****************
	 * Attributes **
	 *****************/

	@Column(name = "NAME")
	private String name;
	@Column(name = "TAG")
	private String tag;

	@Transient	private String actualUserRole;

	public Project() {
	}


	/*****************
	 * Getter & Setters**
	 *****************/

	public List<FileTokenAccess> getFileTokenAccesses() {
		return fileTokenAccesses;
	}

	public void setFileTokenAccesses(List<FileTokenAccess> fileTokenAccesses) {
		this.fileTokenAccesses = fileTokenAccesses;
	}

	public List<Project> getChildProjects() {
		return childProjects;
	}

	public void setChildProjects(List<Project> childProjects) {
		this.childProjects = childProjects;
	}

	public Project getParentProject() {
		return parentProject;
	}

	public void setParentProject(Project parentProject) {
		this.parentProject = parentProject;
	}

	public List<UserProject> getUserProjects() {
		return userProjects;
	}

	public void setUserProjects(List<UserProject> userProjects) {
		this.userProjects = userProjects;
	}

	public List<TestSuite> getTestSuites() {
		return testSuites;
	}

	public void setTestSuites(List<TestSuite> testSuites) {
		this.testSuites = testSuites;
	}

	public List<ProjectVersion> getProjectVersions() {
		return projectVersions;
	}

	public void setProjectVersions(List<ProjectVersion> projectVersions) {
		this.projectVersions = projectVersions;
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

	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}

	@Transient
	public String getActualUserRole() {
		return actualUserRole;
	}
	@Transient
	public void setActualUserRole(String actualUserRole) {
		this.actualUserRole = actualUserRole;
	}
}
