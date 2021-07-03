package tools.mtsuite.core.common.vmodel;

import tools.mtsuite.core.common.model.GenericObject;
import tools.mtsuite.core.endpoint.integrations.dto.BugReporterInputDto;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class VModule extends  VGenericObject{
	/****************
	 * Relationships**
	 *****************/
	private List<VTestCase> testCases = new ArrayList();

	/****************
	 * Attributes **
	 *****************/
	private String name;
	private String order;
	private Integer bugCount = 0;

	// Store FE toggle value
	private Boolean toggle;


	public VModule() {
	}


	/****************
	 * Functions **
	 *****************/

	public void increaseBugCount(String testCaseUId, Long bugLogId){
		testCases.forEach(tc ->{
			if(tc.getUid().equals(testCaseUId)){
				this.bugCount++;
				tc.increaseBugCount(bugLogId);
				return;
			}
		});
	}
	/****************
	 * Constructors **
	 *****************/


	public List<VTestCase> getTestCases() {
		return testCases;
	}

	public void setTestCases(List<VTestCase> testCases) {
		this.testCases = testCases;
	}

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

	public Boolean getToggle() {
		return toggle;
	}

	public void setToggle(Boolean toggle) {
		this.toggle = toggle;
	}

	public Integer getBugCount() {
		return bugCount;
	}

	public void setBugCount(Integer bugCount) {
		this.bugCount = bugCount;
	}
}
