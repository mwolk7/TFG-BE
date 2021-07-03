package tools.mtsuite.core.endpoint.enums;

import org.springframework.stereotype.Service;
import tools.mtsuite.core.common.model.enums.*;
import tools.mtsuite.core.endpoint.enums.dto.Enums;
import tools.mtsuite.core.endpoint.enums.dto.EnumsDto;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class EnumService {

	/**
	*  Return enums
	**/
	public <E extends Enum<E>> List  getEnum(Class<E> enums) {
		List<EnumsDto> retVal = new ArrayList();
		for(E e : enums.getEnumConstants()) {
			retVal.add( new EnumsDto(e.toString(), e.name()));
		}
		return retVal;
	}


}
