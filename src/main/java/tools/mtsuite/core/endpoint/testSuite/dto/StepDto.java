package tools.mtsuite.core.endpoint.testSuite.dto;


import tools.mtsuite.core.common.vmodel.VGenericObject;
import tools.mtsuite.core.core.dto.GenericObjectDto;
import tools.mtsuite.core.core.excepctions.BadRequestException;

import javax.validation.constraints.NotNull;


public class StepDto extends VGenericObject {
	/****************
	 * Relationships**
	 *****************/


	/****************
	 * Attributes **
	 *****************/
	@NotNull(message = "test step must have a name")
	private String name;
	private String order;

	/****************
	 * FUNCTIONS  **
	 ****************/

	public StepDto() {
	}

	public StepDto(String order,String step) {
		this.order = order;
		this.name = step;
	}

	/*******************
	 * GETTES & SETTERS **
	 ********************/

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

	public void validateStep(StepDto stepDto) {
		if (stepDto.getName().isEmpty()) {
			throw new BadRequestException("001", "wrong test case input");
		}
	}


}
