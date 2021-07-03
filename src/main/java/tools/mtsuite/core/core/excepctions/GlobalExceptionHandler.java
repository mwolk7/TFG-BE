package tools.mtsuite.core.core.excepctions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tools.mtsuite.core.core.dto.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {


	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<?> badRequest(BadRequestException badRequestException) {
		return new ResponseEntity(new ResponseStatus(badRequestException.getCode(),	badRequestException.getMessage()), HttpStatus.BAD_REQUEST);
	}
}
