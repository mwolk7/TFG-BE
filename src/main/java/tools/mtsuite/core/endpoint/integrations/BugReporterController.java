package tools.mtsuite.core.endpoint.integrations;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tools.mtsuite.core.endpoint.integrations.dto.*;
import tools.mtsuite.core.endpoint.testRunner.dto.TestSuiteRunnerDto;

import java.util.List;

@RestController
@Tag(name = "bugReporter")
@RequestMapping("/bugReporter")
public class BugReporterController {

    @Autowired
    private BugReporterService bugReporterService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BugReporterCredentialDto getBugReporterCredential(@PathVariable("id") Long id) {
        return bugReporterService.getBugReporter(id);
    }

    @GetMapping("/getAllByCurrentUser")
    @ResponseStatus(HttpStatus.OK)
    public List<BugReporterCredentialDto> getBugReporterCredentialByUser() {
        return bugReporterService.getBugReporterByUser();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public BugReporterCredentialDto createOrUpdateBugReporterCredential(@RequestBody BugReporterCredentialDto bugReporterCredentialDto) {
        if (bugReporterCredentialDto.getId() == null) {
            return bugReporterService.createBugReporterCredential(bugReporterCredentialDto);
        } else {
            return bugReporterService.modifyBugReporterCredential(bugReporterCredentialDto);
        }
    }


    @PostMapping("/{id}/testRunner/{testRunnerId}/reportBug")
    @ResponseStatus(HttpStatus.OK)
    public TestSuiteRunnerDto createOrUpdateBugReporter(@PathVariable("id") Long id,
                                                        @PathVariable("testRunnerId") Long testRunnerId,
                                                        @RequestParam(value = "moduleUId", required = true) String moduleUId,
                                                        @RequestParam(value = "testCaseUId", required = true) String testCaseUId,
                                                        @RequestBody BugReporterInputDto bugReporterInputDto) {
            return bugReporterService.reportBug(id,testRunnerId,moduleUId,testCaseUId,bugReporterInputDto);
    }

    @GetMapping("/{id}/projects")
    @ResponseStatus(HttpStatus.OK)
    public List<BugReporterProjectDto> getBugReporterProjects(@PathVariable("id") Long id) {
        return bugReporterService.getProjectsList(id);
    }


    @GetMapping("/{id}/project/{project}/model")
    @ResponseStatus(HttpStatus.OK)
    public BugReporterModelDto getBugReporterModels(@PathVariable("id") Long id,
                                                    @PathVariable("project") String project) {
        return bugReporterService.getBugReporterModels(id,project);
    }

    @GetMapping("/{id}/logs/testSuiteRunner/{testSuiteRunnerId}/last")
    @ResponseStatus(HttpStatus.OK)
    public BugReporterLogDto getLastBugReporter(@PathVariable("id") Long id, @PathVariable("testSuiteRunnerId") Long testSuiteRunnerId) {
        return bugReporterService.getLastBugReporter(testSuiteRunnerId);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public tools.mtsuite.core.core.dto.ResponseStatus deleteBugReporter(@PathVariable("id") Long id) {
        return bugReporterService.deleteBugReporter(id);
    }

}
