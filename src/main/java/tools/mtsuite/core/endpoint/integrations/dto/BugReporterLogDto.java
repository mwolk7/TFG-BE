package tools.mtsuite.core.endpoint.integrations.dto;

public class BugReporterLogDto {


    /****************
     * Attributes **
     *****************/

    private String bugReporterProject;
    private String requestBody;
    private String issue;

    /****************
     * Getter&Setter **
     *****************/

    public String getBugReporterProject() {
        return bugReporterProject;
    }

    public void setBugReporterProject(String bugReporterProject) {
        this.bugReporterProject = bugReporterProject;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }
}
