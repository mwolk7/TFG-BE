package tools.mtsuite.core.endpoint.testSuite.dto;


import org.apache.commons.csv.CSVRecord;
import tools.mtsuite.core.common.vmodel.VGenericObject;
import tools.mtsuite.core.core.dto.GenericObjectDto;
import tools.mtsuite.core.core.excepctions.BadRequestException;
import tools.mtsuite.core.core.utils.ExcelVariablesPosition;
import tools.mtsuite.core.endpoint.integrations.dto.BugReporterInputDto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ModuleDto extends VGenericObject implements Serializable,Cloneable {
	/****************
	 * Relationships**
	 *****************/

	@NotNull(message = "module must have at least 1 test case")
	@Size(min = 1)
	private List<TestCaseDto> testCases = new ArrayList();

	/****************
	 * Attributes **
	 *****************/
	@NotNull(message = "module must have name")
	@Size(min = 1)
	private String name;
	private String order;
	private Integer bugCount = 0;

	// Store FE toggle value
	private Boolean toggle;

	public ModuleDto() {
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

	public void addTestCaseFromExcelRow(CSVRecord csvRecord){
		String module = csvRecord.get(ExcelVariablesPosition.C_MODULE);

		this.name = module;
		this.getTestCases().add(new TestCaseDto(csvRecord));

	};
	/****************
	 * Constructors **
	 *****************/


	public List<TestCaseDto> getTestCases() {
		return testCases;
	}

	public void setTestCases(List<TestCaseDto> testCases) {
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

	public boolean validateModuleDto(ModuleDto module) {

		for (TestCaseDto t : module.getTestCases()) {
			if (t.getName().isEmpty()) {
				throw new BadRequestException("001", "wrong test case input");
			}
		}
		return true;
	}

	@Override
	public Object clone() {
	try {
		return super.clone();
	}catch (Exception e){
		return this;
	}
	}
}
