package tools.mtsuite.core.common.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import tools.mtsuite.core.common.model.enums.*;
import tools.mtsuite.core.core.utils.AppConstants;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = AppConstants.TEST_SUITE)
public class TestSuite extends GenericObject {
	/****************
	 * Relationships**
	 *****************/

	@Column(name = "MODULES")
	@Lob
	private String modules;

	@OneToMany(mappedBy = "testSuite", fetch = FetchType.LAZY)
	@Cascade({CascadeType.SAVE_UPDATE})
	private List<TestSuiteRunner> testSuiteRunners = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_ID")
	@Cascade({CascadeType.SAVE_UPDATE})
	private Project project;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATOR_USER_ID")
	@Cascade({CascadeType.SAVE_UPDATE})
	private User creatorUser;


	/****************
	 * Attributes **
	 *****************/

	@Column(name = "NAME")
	private String name;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "TEST_SUITE_VERSION")
	private String testSuiteVersion;

	@Column(name = "DEVICES")
	@Enumerated(EnumType.STRING)
	@ElementCollection(targetClass = Devices.class, fetch = FetchType.LAZY)
	private List<Devices> devices = new ArrayList() ;

	@Column(name = "OS")
	@Enumerated(EnumType.STRING)
	@ElementCollection(targetClass = OS.class, fetch = FetchType.LAZY)
	private List<OS> os = new ArrayList() ;

	@Column(name = "BROWSERS")
	@Enumerated(EnumType.STRING)
	@ElementCollection(targetClass = Browsers.class, fetch = FetchType.LAZY)
	private List<Browsers> browsers = new ArrayList() ;

	/****************
	 * Constructors **
	 *****************/
	public TestSuite() {
	}

	/****************
	 * getter & setter **
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

	public User getCreatorUser() {
		return creatorUser;
	}

	public void setCreatorUser(User creatorUser) {
		this.creatorUser = creatorUser;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTestSuiteVersion() {
		return testSuiteVersion;
	}

	public void setTestSuiteVersion(String testSuiteVersion) {
		this.testSuiteVersion = testSuiteVersion;
	}

	public List<Devices> getDevices() {
		return devices;
	}

	public void setDevices(List<Devices> devices) {
		this.devices = devices;
	}

	public List<OS> getOs() {
		return os;
	}

	public void setOs(List<OS> os) {
		this.os = os;
	}

	public List<Browsers> getBrowsers() {
		return browsers;
	}

	public void setBrowsers(List<Browsers> browsers) {
		this.browsers = browsers;
	}

	public String getModules() {
		return modules;
	}

	public void setModules(String modules) {
		this.modules = modules;
	}
}
