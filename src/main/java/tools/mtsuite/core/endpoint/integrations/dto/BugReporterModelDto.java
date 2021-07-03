package tools.mtsuite.core.endpoint.integrations.dto;

import java.util.ArrayList;
import java.util.List;

public class BugReporterModelDto {

    private List<ElementListDto> issuesType   = new ArrayList();
    private List<ElementListDto> priorities   = new ArrayList();
    private List<ElementListDto> categories   = new ArrayList();
    private List<ElementListDto> versions     = new ArrayList();
    private List<ElementListDto> milestones     = new ArrayList();
    private List<ElementListDto> projectUsers = new ArrayList();
    private ElementListDto currentUser;


    public List<ElementListDto> getIssuesType() {
        return issuesType;
    }

    public void setIssuesType(List<ElementListDto> issuesType) {
        this.issuesType = issuesType;
    }

    public List<ElementListDto> getPriorities() {
        return priorities;
    }

    public void setPriorities(List<ElementListDto> priorities) {
        this.priorities = priorities;
    }

    public List<ElementListDto> getCategories() {
        return categories;
    }

    public void setCategories(List<ElementListDto> categories) {
        this.categories = categories;
    }

    public List<ElementListDto> getVersions() {
        return versions;
    }

    public void setVersions(List<ElementListDto> versions) {
        this.versions = versions;
    }

    public List<ElementListDto> getProjectUsers() {
        return projectUsers;
    }

    public void setProjectUsers(List<ElementListDto> projectUsers) {
        this.projectUsers = projectUsers;
    }

    public ElementListDto getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(ElementListDto currentUser) {
        this.currentUser = currentUser;
    }

    public List<ElementListDto> getMilestones() {
        return milestones;
    }

    public void setMilestones(List<ElementListDto> milestones) {
        this.milestones = milestones;
    }
}
