package tools.mtsuite.core.endpoint.enums;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tools.mtsuite.core.endpoint.enums.dto.Enums;
import tools.mtsuite.core.endpoint.enums.dto.EnumsDto;

import java.util.HashMap;
import java.util.List;

@RestController
@Tag(name = "enum")
@RequestMapping("/enum")
public class EnumController {

	@Autowired
	private EnumService enumService;

	@GetMapping("/{enums}")
	@ResponseStatus(HttpStatus.OK)
	public  <E extends Enum<E>> List getEnum(@PathVariable Enums enums ) {
		return enumService.getEnum(enums.getValue());
	}

}
