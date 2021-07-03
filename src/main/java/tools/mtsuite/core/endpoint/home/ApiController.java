package tools.mtsuite.core.endpoint.home;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tools.mtsuite.core.core.dto.LoginUserDto;
import tools.mtsuite.core.core.dto.ResponseStatus;

@RestController
@Tag(name = "Api")
public class ApiController {

	@GetMapping("/healthcheck")
	@org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.OK)
	public ResponseStatus healthcheck() {
		return new ResponseStatus("200","Server is running...");
	}


}
