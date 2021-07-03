package tools.mtsuite.core.endpoint.testRunner.dto;

import tools.mtsuite.core.common.model.enums.Browsers;
import tools.mtsuite.core.common.model.enums.Devices;
import tools.mtsuite.core.common.model.enums.OS;
import tools.mtsuite.core.common.model.enums.TestSuiteStatus;
import tools.mtsuite.core.core.dto.GenericObjectDto;
import tools.mtsuite.core.endpoint.profile.dto.UserDto;
import tools.mtsuite.core.endpoint.testSuite.dto.ModuleDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class TestSuiteRunnerTableDto extends GenericObjectDto {
	/****************
	 * Relationships**
	 *****************/

	private Long creatorUserId;
	private Long testSuiteId;
	private String testSuiteName;


	private ProjectVersionDto projectVersion;
	private TestSuiteRunnerStatisticsDto statistics;

	/****************
	 * Attributes **
	 *****************/

	private String name;
	private Date startDate;
	private Date finishDate;
	private Date dueDate;
	private TestSuiteStatus status;
	private List<UserDto> users = new ArrayList();
	private boolean isChecked;

	/****************
	 * Constructors **
	 *****************/
	public TestSuiteRunnerTableDto() {
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

	public String getTestSuiteName() {
		return testSuiteName;
	}

	public void setTestSuiteName(String testSuiteName) {
		this.testSuiteName = testSuiteName;
	}

	public ProjectVersionDto getProjectVersion() {
		return projectVersion;
	}

	public void setProjectVersion(ProjectVersionDto projectVersion) {
		this.projectVersion = projectVersion;
	}

	public TestSuiteRunnerStatisticsDto getStatistics() {
		return statistics;
	}

	public void setStatistics(TestSuiteRunnerStatisticsDto statistics) {
		this.statistics = statistics;
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

	public boolean isChecked() { return isChecked; }

	public void setChecked(boolean checked) { isChecked = checked; }

	public List<UserDto> getUsers() {
		return users;
	}

	public void setUsers(List<UserDto> users) {
		this.users = users;
	}
}

