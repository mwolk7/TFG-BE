package tools.mtsuite.core.core.excepctions;

public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String code;
	private String message;


	public BadRequestException(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
