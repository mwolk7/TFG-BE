package tools.mtsuite.core.common.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import tools.mtsuite.core.core.utils.AppConstants;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = AppConstants.PROJECT_VERSIONS)
public class ProjectVersion extends GenericObject {
	/****************
	 * Relationships**
	 *****************/

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_ID")
	@Cascade({CascadeType.SAVE_UPDATE})
	private Project project;


	@OneToMany(mappedBy = "projectVersion", fetch = FetchType.LAZY)
	@Cascade({ CascadeType.SAVE_UPDATE })
	private List<TestSuiteRunner> testSuiteRunners = new ArrayList();

	/****************
	 * Attributes **
	 *****************/
	@Column(name = "PROJECT_VERSION")
	private String project_version;

	public ProjectVersion() {
	}


	/****************
	 * Constructors **
	 *****************/

	public List<TestSuiteRunner> getTestSuiteRunners() {
		return testSuiteRunners;
	}

	public void setTestSuiteRunners(List<TestSuiteRunner> testSuiteRunners) {
		this.testSuiteRunners = testSuiteRunners;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getProject_version() {
		return project_version;
	}

	public void setProject_version(String project_version) {
		this.project_version = project_version;
	}
}
