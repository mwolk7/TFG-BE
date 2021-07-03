package tools.mtsuite.core.endpoint.testRunner.dto;

import tools.mtsuite.core.common.model.ProjectVersion;
import tools.mtsuite.core.common.model.TestSuiteRunner;
import tools.mtsuite.core.common.model.TestSuiteRunnerStatistics;
import tools.mtsuite.core.common.model.enums.Browsers;
import tools.mtsuite.core.common.model.enums.Devices;
import tools.mtsuite.core.common.model.enums.OS;
import tools.mtsuite.core.common.model.enums.TestSuiteStatus;
import tools.mtsuite.core.core.dto.GenericObjectDto;
import tools.mtsuite.core.endpoint.integrations.dto.BugReporterInputDto;
import tools.mtsuite.core.endpoint.profile.dto.UserDto;
import tools.mtsuite.core.endpoint.testSuite.dto.ModuleDto;

import javax.validation.constraints.*;
import java.util.*;


public class TestSuiteRunnerDto extends GenericObjectDto {
	/****************
	 * Relationships**
	 *****************/

	private Long creatorUserId;

	@NotNull(message = "test suite runner ID is mandatory")
	private Long testSuiteId;

	@Size(min=1, max=50)
	private String testSuiteName;

	@NotNull(message = "test suite runner must have at least 1 module")
	@Size(min=1)
	private List<ModuleDto> modules = new ArrayList();

	private List<UserDto> users = new ArrayList();

	private ProjectVersionDto projectVersion;
	private TestSuiteRunnerStatisticsDto statistics;

	/****************
	 * Attributes **
	 *****************/

	@NotEmpty(message = "test suite runner name is mandatory")
	@Size(min=1, max=20)
	private String name;

	private Date startDate;

	private Date finishDate;

	private Date dueDate;
	private TestSuiteStatus status;
	private List<Devices> devices = new ArrayList();
	private List<OS> os = new ArrayList();
	private List<Browsers> browsers = new ArrayList();

	/****************
	 * Functions **
	 *****************/

	public void increaseBugCountModule(String moduleUId,String testCaseUId, Long bugLogId){
		increaseBugCountStatistic();

		this.modules.forEach(m ->{
			if(m.getUid().equals(moduleUId)) {
				m.increaseBugCount( testCaseUId, bugLogId);
				return;
			}
		});
	}

	private void increaseBugCountStatistic() {
		this.statistics.increaseBugCount();
	}

	/****************
	 * Constructors **
	 *****************/
	public TestSuiteRunnerDto() {
	}


	/****************
	 * getter & setter **
	 *****************/

	public Long getCreatorUserId() {
		return creatorUserId;
	}

	public void setCreatorUserId(Long creatorUserId) {
		this.creatorUserId = creatorUserId;
	}

	public Long getTestSuiteId() {
		return testSuiteId;
	}

	public void setTestSuiteId(Long testSuiteId) {
		this.testSuiteId = testSuiteId;
	}

	public TestSuiteRunnerStatisticsDto getStatistics() {
		return statistics;
	}

	public void setStatistics(TestSuiteRunnerStatisticsDto statistics) {
		this.statistics = statistics;
	}

	public List<ModuleDto> getModules() {
		return modules;
	}

	public void setModules(List<ModuleDto> modules) {
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

	public String getTestSuiteName() { return testSuiteName; }

	public void setTestSuiteName(String testSuiteName) { this.testSuiteName = testSuiteName; }

	public List<UserDto> getUsers() { return users; }

	public void setUsers(List<UserDto> users) { this.users = users; }

	public ProjectVersionDto getProjectVersion() { return projectVersion; }

	public void setProjectVersion(ProjectVersionDto projectVersion) { this.projectVersion = projectVersion; }

	public List<Devices> getDevices() { return devices; }

	public void setDevices(List<Devices> devices) { this.devices = devices; }

	public List<OS> getOs() { return os; }

	public void setOs(List<OS> os) { this.os = os; }

	public List<Browsers> getBrowsers() { return browsers; }

	public void setBrowsers(List<Browsers> browsers) { this.browsers = browsers; }
}

