package tools.mtsuite.core.endpoint.testRunner;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tools.mtsuite.core.common.model.enums.TestSuiteStatus;
import tools.mtsuite.core.core.interceptor.acl.ProjectACL;
import tools.mtsuite.core.core.interceptor.acl.TestSuiteACL;
import tools.mtsuite.core.core.interceptor.acl.TestSuiteRunnerACL;
import tools.mtsuite.core.core.interceptor.visibility.Visibility;
import tools.mtsuite.core.endpoint.enums.EnumService;
import tools.mtsuite.core.endpoint.testRunner.dto.TestSuiteRunnerDto;
import tools.mtsuite.core.endpoint.testRunner.dto.TestSuiteRunnerPaginationDto;
import tools.mtsuite.core.endpoint.testRunner.dto.TestSuiteRunnerTableDto;

import javax.validation.Valid;
import java.util.List;


@RestController
@Tag(name = "testSuiteRunner")
@RequestMapping("/testSuiteRunner")
public class TestSuiteRunnerController {

    @Autowired
    private TestSuiteRunnerService testSuiteRunnerService;

    /**
     * get all testSuiteRunners
     * @return
     */
    @GetMapping("/testSuite/{testSuiteId}")
    @ResponseStatus(HttpStatus.OK)
    @Visibility(attr = "testSuiteId",acl = TestSuiteACL.class)
    public TestSuiteRunnerPaginationDto getTestSuiteRunnersByTestSuite(@PathVariable String testSuiteId,
                                                                             @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                                             @RequestParam(value = "size", required = false, defaultValue = "20") Integer size,
                                                                             @RequestParam(value = "searchFilters", required = false) List<TestSuiteStatus> searchFilters) {
        return testSuiteRunnerService.getTestSuiteRunnersByTestSuite(Long.valueOf(testSuiteId), page, size,searchFilters);
    }

    /**
     * get test suite
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Visibility(attr = "id",acl = TestSuiteRunnerACL.class)
    public TestSuiteRunnerDto getTestSuiteRunnerById(@PathVariable String id) {
        return testSuiteRunnerService.getTestSuiteRunner(Long.valueOf(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Visibility(attr = "id",acl = TestSuiteRunnerACL.class)
    public Boolean deleteTestSuiteRunnerById(@PathVariable String id) {
        return testSuiteRunnerService.deleteTestSuiteRunner(Long.valueOf(id));
    }

    /**
     * set test suite
     * @param testSuiteRunnerDto
     * @return
     */
    @PostMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public TestSuiteRunnerDto setTestSuiteRunner(@RequestBody @Valid TestSuiteRunnerDto testSuiteRunnerDto) {

        if( testSuiteRunnerDto.getId() == null) {
            return testSuiteRunnerService.addTestSuiteRunner(testSuiteRunnerDto);
        } else {
            return testSuiteRunnerService.updateTestSuiteRunner(testSuiteRunnerDto);
        }
    }

    /**
     * set test suite
     * @param Long testSuiteRunnerId
     * @return
     */
    @GetMapping("/run/{testSuiteRunnerId}")
    @ResponseStatus(HttpStatus.OK)
    @Visibility(attr = "testSuiteRunnerId",acl = TestSuiteRunnerACL.class)
    public TestSuiteRunnerDto runTestSuiteRunner(@PathVariable Long testSuiteRunnerId) {
            return testSuiteRunnerService.updateTestSuiteRunnerStatus(testSuiteRunnerId, TestSuiteStatus.Running);
    }

    /**
     * set test suite
     * @param Long testSuiteRunnerId
     * @return
     */
    @GetMapping("/pause/{testSuiteRunnerId}")
    @ResponseStatus(HttpStatus.OK)
    @Visibility(attr = "testSuiteRunnerId",acl = TestSuiteRunnerACL.class)
    public TestSuiteRunnerDto pauseTestSuiteRunner(@PathVariable Long testSuiteRunnerId) {
        return testSuiteRunnerService.updateTestSuiteRunnerStatus(testSuiteRunnerId, TestSuiteStatus.Waiting);
    }

    /**
     * set test suite
     * @param Long testSuiteRunnerId
     * @return
     */
    @GetMapping("/cancel/{testSuiteRunnerId}")
    @ResponseStatus(HttpStatus.OK)
    @Visibility(attr = "testSuiteRunnerId",acl = TestSuiteRunnerACL.class)
    public TestSuiteRunnerDto cancelTestSuiteRunner(@PathVariable Long testSuiteRunnerId) {
        return testSuiteRunnerService.updateTestSuiteRunnerStatus(testSuiteRunnerId, TestSuiteStatus.Cancelled);
    }

    /**
     * set test suite
     * @param Long testSuiteRunnerId
     * @return
     */
    @GetMapping("/finish/{testSuiteRunnerId}")
    @ResponseStatus(HttpStatus.OK)
    @Visibility(attr = "testSuiteRunnerId",acl = TestSuiteRunnerACL.class)
    public TestSuiteRunnerDto finishTestSuiteRunner(@PathVariable Long testSuiteRunnerId) {
        return testSuiteRunnerService.updateTestSuiteRunnerStatus(testSuiteRunnerId, TestSuiteStatus.Finished);
    }

}
