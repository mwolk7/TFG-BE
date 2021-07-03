package tools.mtsuite.core.common.vmodel;

import tools.mtsuite.core.common.model.File;
import tools.mtsuite.core.common.model.GenericObject;
import tools.mtsuite.core.common.model.User;
import tools.mtsuite.core.common.model.enums.Priorities;
import tools.mtsuite.core.common.model.enums.TestCaseStatus;
import tools.mtsuite.core.endpoint.integrations.dto.BugReporterInputDto;

import javax.lang.model.type.ArrayType;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;

public class VTestCase  extends  VGenericObject {
	/****************
	 * Relationships**
	 *****************/

	private List<File> files = new ArrayList();
	private User assignedUser;
	private List<VStep> steps = new ArrayList();

	/****************
	 * Attributes **
	 *****************/
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

	public VTestCase() {
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

	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}

	public User getAssignedUser() {
		return assignedUser;
	}

	public void setAssignedUser(User assignedUser) {
		this.assignedUser = assignedUser;
	}

	public List<VStep> getSteps() {
		return steps;
	}

	public void setSteps(List<VStep> steps) {
		this.steps = steps;
	}

	public Boolean getRun() {
		return run;
	}

	public void setRun(Boolean run) {
		this.run = run;
	}

	public Boolean getToggle() { return toggle; }

	public void setToggle(Boolean toggle) {
		this.toggle = toggle;
	}

	public Integer getBugCount() { return bugCount; }

	public void setBugCount(Integer bugCount) { this.bugCount = bugCount; }

	public List<Long> getIdBugsReported() { return idBugsReported; }

	public void setIdBugsReported(List<Long> idBugsReported) { this.idBugsReported = idBugsReported; }
}
