package tools.mtsuite.core.endpoint.files.dto;


import tools.mtsuite.core.core.dto.GenericObjectDto;


public class FileTokenAccessDto {
	/****************
	 * Relationships**
	 *****************/

	/****************
	 * Attributes **
	 *****************/
	private String token;




	/****************
	 * FUNCTIONS  **
	 *****************/

	public FileTokenAccessDto() {
	}
	public FileTokenAccessDto(String token) {
		this.token = token;
	}

	/*******************
	 * GETTES & SETTERS **
	 ********************/

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
