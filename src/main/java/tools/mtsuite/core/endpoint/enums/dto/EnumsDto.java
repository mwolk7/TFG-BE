package tools.mtsuite.core.endpoint.enums.dto;


import tools.mtsuite.core.common.model.TestSuite;
import tools.mtsuite.core.core.dto.GenericObjectDto;
import tools.mtsuite.core.endpoint.testSuite.dto.TestCaseDto;


public class EnumsDto {
	/****************
	 * Relationships**
	 *****************/

	private String key;
	private String value;

	/****************
	 * Attributes **
	 *****************/

	public EnumsDto(String value, String key) {
		this.key = key;
		this.value = value;
	}

	/****************
	 * Constructors **
	 *****************/
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
