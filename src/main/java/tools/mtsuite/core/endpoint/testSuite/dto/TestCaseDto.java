package tools.mtsuite.core.endpoint.testSuite.dto;


import org.apache.commons.csv.CSVRecord;
import tools.mtsuite.core.common.integrations.backlog.dto.Priority;
import tools.mtsuite.core.common.model.enums.Priorities;
import tools.mtsuite.core.common.model.enums.TestCaseStatus;
import tools.mtsuite.core.common.vmodel.VGenericObject;
import tools.mtsuite.core.core.dto.GenericObjectDto;
import tools.mtsuite.core.core.excepctions.BadRequestException;
import tools.mtsuite.core.core.utils.ExcelVariablesPosition;
import tools.mtsuite.core.endpoint.files.dto.FileDto;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;



public class TestCaseDto extends VGenericObject {
	/****************
	 * Relationships**
	 *****************/

	private List<FileDto> files = new ArrayList();


	private List<StepDto> steps = new ArrayList();

	/****************
	 * Attributes **
	 *****************/

	@NotNull(message = "test case must have name")
	@Size(min = 1)
	private String name;
	private String order;
	private String precondition;
	private String expectedResult;
	private Integer estimatedTime;
	private Boolean forceAttachment;
	private String commet;
	private TestCaseStatus testCaseStatus;
	private Priorities priority;
	private Integer bugCount = 0;

	private List<Long> idBugsReported  = new ArrayList<>();

	// Store if testcase is selected to run
	private Boolean run;

	// Store FE toggle value
	private Boolean toggle;

	public List<FileDto> getFiles() {
		return files;
	}

	public void setFiles(List<FileDto> files) {
		this.files = files;
	}

	public List<StepDto> getSteps() {
		return steps;
	}

	public void setSteps(List<StepDto> steps) {
		this.steps = steps;
	}

	/****************
	 * Functions **
	 *****************/

	public void increaseBugCount(Long bugLogId){
		this.bugCount++;
		this.idBugsReported.add(bugLogId);
	}

	/****************
	 * Constructors **
	 *****************/

	public TestCaseDto() {
	}


	public TestCaseDto(CSVRecord csvRecord){
		String testCase = csvRecord.get(ExcelVariablesPosition.C_TEST_CASE);
		String precondition = csvRecord.get(ExcelVariablesPosition.C_PRECONDITION);
		String expectedResult = csvRecord.get(ExcelVariablesPosition.C_EXPECTED_RESULT);
		String priority = csvRecord.get(ExcelVariablesPosition.C_PRIORITY);
		String steps = csvRecord.get(ExcelVariablesPosition.C_STEPS);

		this.name = testCase;
		this.precondition = precondition;
		this.priority = priority != null && !priority.isEmpty() ? Priorities.valueOf(priority):null;
		this.expectedResult = expectedResult;


		int i = 1;
		for (String step : steps.split("\\|\\|"))	 {
			this.steps.add(new StepDto(String.valueOf(i),step));
			i++;
		}
	}


	/****************
	 * getter&setter **
	 *****************/
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getPrecondition() {
		return precondition;
	}

	public void setPrecondition(String precondition) {
		this.precondition = precondition;
	}

	public String getExpectedResult() {
		return expectedResult;
	}

	public void setExpectedResult(String expectedResult) {
		this.expectedResult = expectedResult;
	}

	public Integer getEstimatedTime() {
		return estimatedTime;
	}

	public void setEstimatedTime(Integer estimatedTime) {
		this.estimatedTime = estimatedTime;
	}

	public Boolean getForceAttachment() {
		return forceAttachment;
	}

	public void setForceAttachment(Boolean forceAttachment) {
		this.forceAttachment = forceAttachment;
	}

	public String getCommet() {
		return commet;
	}

	public void setCommet(String commet) {
		this.commet = commet;
	}

	public TestCaseStatus getTestCaseStatus() {
		return testCaseStatus;
	}

	public void setTestCaseStatus(TestCaseStatus testCaseStatus) {
		this.testCaseStatus = testCaseStatus;
	}

	public Priorities getPriority() {
		return priority;
	}

	public void setPriority(Priorities priority) {
		this.priority = priority;
	}

	public Boolean getRun() {
		return run;
	}

	public void setRun(Boolean run) {
		this.run = run;
	}

	public Boolean getToggle() {
		return toggle;
	}

	public void setToggle(Boolean toggle) {
		this.toggle = toggle;
	}

	public Integer getBugCount() { return bugCount; }

	public void setBugCount(Integer bugCount) { this.bugCount = bugCount; }

	public List<Long> getIdBugsReported() { return idBugsReported; }

	public void setIdBugsReported(List<Long> idBugsReported) { this.idBugsReported = idBugsReported; }

	public void validateTestCase(TestCaseDto testCaseDto) {
		if (testCaseDto.getName().isEmpty() && testCaseDto.getSteps().isEmpty()) {
			throw new BadRequestException("001", "wrong test case input");
		}
	}
}
