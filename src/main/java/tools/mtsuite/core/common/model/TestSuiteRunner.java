package tools.mtsuite.core.common.model;


import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import tools.mtsuite.core.common.model.enums.Browsers;
import tools.mtsuite.core.common.model.enums.Devices;
import tools.mtsuite.core.common.model.enums.OS;
import tools.mtsuite.core.common.model.enums.TestSuiteStatus;
import tools.mtsuite.core.core.utils.AppConstants;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = AppConstants.TEST_SUITE_RUNNERS)
public class TestSuiteRunner extends GenericObject {
	/****************
	 * Relationships**
	 *****************/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATOR_USER_ID")
	@Cascade({CascadeType.SAVE_UPDATE})
	private User creatorUser;

	@ManyToMany(fetch = FetchType.LAZY)
	@Cascade({ CascadeType.SAVE_UPDATE })
	@JoinTable(name="USERS_TEST_SUITE_RUNNERS",
			joinColumns={@JoinColumn(name = "TEST_SUIT_RUNNER_ID")},
			inverseJoinColumns={@JoinColumn(name = "USER_ID")})
	private List<User> users = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_VERSION_ID")
	@Cascade({CascadeType.SAVE_UPDATE})
	private ProjectVersion projectVersion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TEST_SUIT_ID")
	@Cascade({CascadeType.SAVE_UPDATE})
	private TestSuite testSuite;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STATISTICS")
	@Cascade({CascadeType.SAVE_UPDATE})
	private TestSuiteRunnerStatistics statistics;

	@Column(name = "MODULES")
	@Lob
	private String modules;

	/****************
	 * Attributes **
	 *****************/
	@Column(name = "NAME")
	private String name;

	@Column(name = "START_DATE")
	private Date startDate;

	@Column(name = "FINISH_DATE")
	private Date finishDate;

	@Column(name = "DUE_DATE")
	private Date dueDate;

	@Column(name = "STATUS")
	@Enumerated(EnumType.STRING)
	private TestSuiteStatus status;

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
	 * Functions **
	 *****************/

	public void increaseBugCount() {
		this.statistics.increaseBugCount();
	}

	/****************
	 * Constructors **
	 *****************/

	public TestSuiteRunner() {
	}

	/****************
	 * Getters & Setters **
	 *****************/

	public User getCreatorUser() {
		return creatorUser;
	}

	public void setCreatorUser(User creatorUser) {
		this.creatorUser = creatorUser;
	}

	public ProjectVersion getProjectVersion() {
		return projectVersion;
	}

	public void setProjectVersion(ProjectVersion projectVersion) {
		this.projectVersion = projectVersion;
	}

	public TestSuite getTestSuite() {
		return testSuite;
	}

	public void setTestSuite(TestSuite testSuite) {
		this.testSuite = testSuite;
	}

	public TestSuiteRunnerStatistics getStatistics() {
		return statistics;
	}

	public void setStatistics(TestSuiteRunnerStatistics statistics) {
		this.statistics = statistics;
	}

	public String getModules() {
		return modules;
	}

	public void setModules(String modules) {
		this.modules = modules;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public TestSuiteStatus getStatus() {
		return status;
	}

	public void setStatus(TestSuiteStatus status) {
		this.status = status;
	}

	public List<User> getUsers() { return users; }

	public void setUsers(List<User> users) { this.users = users; }

	public List<Devices> getDevices() { return devices; }

	public void setDevices(List<Devices> devices) { this.devices = devices; }

	public List<OS> getOs() { return os; }

	public void setOs(List<OS> os) { this.os = os; }

	public List<Browsers> getBrowsers() { return browsers; }

	public void setBrowsers(List<Browsers> browsers) { this.browsers = browsers; }
}
