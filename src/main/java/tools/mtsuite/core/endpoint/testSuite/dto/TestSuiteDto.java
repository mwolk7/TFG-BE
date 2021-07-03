package tools.mtsuite.core.endpoint.testSuite.dto;

import tools.mtsuite.core.common.model.TestSuite;
import tools.mtsuite.core.common.model.TestSuiteRunner;
import tools.mtsuite.core.common.model.enums.Browsers;
import tools.mtsuite.core.common.model.enums.Devices;
import tools.mtsuite.core.common.model.enums.OS;
import tools.mtsuite.core.common.model.enums.TestSuiteStatus;
import tools.mtsuite.core.common.vmodel.VTestSuite;
import tools.mtsuite.core.core.context.Mapeador;
import tools.mtsuite.core.core.dto.GenericObjectDto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


public class TestSuiteDto extends GenericObjectDto {
	/****************
	 * Relationships**
	 *****************/

	@NotNull(message = "test suite must have at least 1 module")
	@Size(min=1)
	private List<ModuleDto> modules = new ArrayList();
	private Long projectId;
	private Long creatorUserId;

	// statistics
	private Integer totalTestRunners;
	private Integer totalTestRunnersRunning;
	private Integer totalTestCases;
	private Integer totalTestCasesCritical;
	private Integer totalTestCasesHigh;
	private Integer totalTestCasesMedium;
	private Integer totalTestCasesLow;



	/****************
	 * Attributes **
	 *****************/

	@NotEmpty(message = "test suite must have a valid name")
	@Size(min=1)
	private String name;

	private String description;
	private String testSuiteVersion;
	private List<Devices> devices = new ArrayList();
	private List<OS> os = new ArrayList();
	private List<Browsers> browsers = new ArrayList();

	/****************
	 * Constructors **
	 *****************/
	public TestSuiteDto() {
	}

	/****************
	 * Functions **
	 *****************/




	/****************
	 * getter & setter **
	 *****************/

	public List<ModuleDto> getModules() {
		return modules;
	}

	public void setModules(List<ModuleDto> modules) {
		this.modules = modules;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getCreatorUserId() {
		return creatorUserId;
	}

	public void setCreatorUserId(Long creatorUserId) {
		this.creatorUserId = creatorUserId;
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

	public List<Devices> getDevices() { return devices; }

	public void setDevices(List<Devices> devices) { this.devices = devices; }

	public List<OS> getOs() { return os; }

	public void setOs(List<OS> os) { this.os = os; }

	public List<Browsers> getBrowsers() { return browsers; }

	public void setBrowsers(List<Browsers> browsers) { this.browsers = browsers; }

	public Integer getTotalTestRunners() {
		return totalTestRunners;
	}

	public void setTotalTestRunners(Integer totalTestRunners) {
		this.totalTestRunners = totalTestRunners;
	}

	public Integer getTotalTestRunnersRunning() {
		return totalTestRunnersRunning;
	}

	public void setTotalTestRunnersRunning(Integer totalTestRunnersRunning) {
		this.totalTestRunnersRunning = totalTestRunnersRunning;
	}

	public Integer getTotalTestCases() {
		return totalTestCases;
	}

	public void setTotalTestCases(Integer totalTestCases) {
		this.totalTestCases = totalTestCases;
	}

	public Integer getTotalTestCasesCritical() {
		return totalTestCasesCritical;
	}

	public void setTotalTestCasesCritical(Integer totalTestCasesCritical) {
		this.totalTestCasesCritical = totalTestCasesCritical;
	}

	public Integer getTotalTestCasesHigh() {
		return totalTestCasesHigh;
	}

	public void setTotalTestCasesHigh(Integer totalTestCasesHigh) {
		this.totalTestCasesHigh = totalTestCasesHigh;
	}

	public Integer getTotalTestCasesMedium() {
		return totalTestCasesMedium;
	}

	public void setTotalTestCasesMedium(Integer totalTestCasesMedium) {
		this.totalTestCasesMedium = totalTestCasesMedium;
	}

	public Integer getTotalTestCasesLow() {
		return totalTestCasesLow;
	}

	public void setTotalTestCasesLow(Integer totalTestCasesLow) {
		this.totalTestCasesLow = totalTestCasesLow;
	}
}

