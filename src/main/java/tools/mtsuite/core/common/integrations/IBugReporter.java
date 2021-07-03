package tools.mtsuite.core.common.integrations;

import tools.mtsuite.core.common.model.integrations.BugReporterCredential;
import tools.mtsuite.core.endpoint.integrations.dto.BugReporterInputDto;
import tools.mtsuite.core.endpoint.integrations.dto.BugReporterProjectDto;
import tools.mtsuite.core.endpoint.integrations.dto.ElementListDto;

import java.util.List;

public interface IBugReporter {
    BugReporterType getType();


    List<BugReporterProjectDto> getProjects(BugReporterCredential bugReporterCredential);
    List<ElementListDto> getIssuesType(BugReporterCredential bugReporterCredential,String projectId);
    List<ElementListDto> getPriorities(BugReporterCredential bugReporterCredential);
    List<ElementListDto> getMilestones(BugReporterCredential bugReporterCredential,String projectId);
    List<ElementListDto> getUsersProject(BugReporterCredential bugReporterCredential,String projectId);
    List<ElementListDto> getCategories(BugReporterCredential bugReporterCredential,String projectId);
    List<ElementListDto> getVersions(BugReporterCredential bugReporterCredential,String projectId);
    ElementListDto getUserMySelf(BugReporterCredential bugReporterCredential);

    String reportBug(BugReporterCredential bugReporterCredential,BugReporterInputDto bugInput);
    
}
