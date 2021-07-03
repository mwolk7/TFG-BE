package tools.mtsuite.core.endpoint.testSuite;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tools.mtsuite.core.core.excepctions.BadRequestException;
import tools.mtsuite.core.core.interceptor.acl.ProjectACL;
import tools.mtsuite.core.core.interceptor.acl.TestSuiteACL;
import tools.mtsuite.core.core.interceptor.visibility.Visibility;
import tools.mtsuite.core.endpoint.testSuite.dto.TestSuiteDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@Tag(name = "testSuite")
@RequestMapping("/testSuite")
public class TestSuiteController {

	@Autowired
	private TestSuiteService testSuiteService;

	/**
	 * get all testSuites
	 * @param projectId
	 * @return
	 */
	@GetMapping("/proyect/{projectId}")
	@ResponseStatus(HttpStatus.OK)
	@Visibility(attr = "projectId",acl = ProjectACL.class)
	public List<TestSuiteDto> getTestSuitesByProject(@PathVariable String projectId) {
		return testSuiteService.getTestSuitesByProject(Long.valueOf(projectId));
	}

	/**
	 * get test suite
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@Visibility(attr = "id",acl = TestSuiteACL.class)
	public TestSuiteDto getTestSuiteById(@PathVariable String id) {
		return testSuiteService.getTestSuite(Long.valueOf(id));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@Visibility(attr = "id",acl = TestSuiteACL.class)
	public Boolean deleteTestSuiteById(@PathVariable String id) {
		return testSuiteService.deleteTestSuite(Long.valueOf(id));
	}

	/**
	 * set test suite
	 * @param testSuiteDto
	 * @return
	 */
	@PostMapping("/")
	@ResponseStatus(HttpStatus.OK)
	public TestSuiteDto setTestSuite(@RequestBody @Valid TestSuiteDto testSuiteDto) {

		if( testSuiteDto.getId() == null) {
			return testSuiteService.addTestSuite(testSuiteDto);
		} else {
			return testSuiteService.updateTestSuite(testSuiteDto);
		}
	}

}
