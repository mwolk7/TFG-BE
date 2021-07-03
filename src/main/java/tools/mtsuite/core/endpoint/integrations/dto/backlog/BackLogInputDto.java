package tools.mtsuite.core.endpoint.integrations.dto.backlog;

import org.aspectj.apache.bcel.ExceptionConstants;
import org.checkerframework.checker.formatter.qual.Format;
import tools.mtsuite.core.common.model.integrations.BacklogCredential;
import tools.mtsuite.core.common.model.integrations.BugReporterCredential;
import tools.mtsuite.core.endpoint.integrations.dto.BugReporterInputDto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class BackLogInputDto extends BugReporterInputDto {


    /****************
     * Attributes**
     *****************/

    @NotNull(message = "Project ID is mandatory")
    @Size(min=0)
    private Integer projectId;

    @NotNull(message = "Summary is mandatory")
    private String summary;

    @Size(min=0)
    private Integer parentIssueId;
    private String description;

    @Pattern(regexp = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$")
    private String startDate;

    @Pattern(regexp = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$")
    private String dueDate;

    @Size(min=0)
    private Integer estimatedHours;

    @Size(min=0)
    private Integer actualHours;

    @NotNull(message = "Issue Type is mandatory")
    @Size(min=0)
    private Integer issueTypeId;

    private Integer[] categoryId;
    private Integer[] versionId;
    private Integer[] milestoneId;

    @NotNull(message = "Priority Type is mandatory")
    @Size(min=0)
    private Integer priorityId;

    @Size(min=0)
    private Integer assigneeId;

    private Integer[] notifiedUserId;
    private Integer[] attachmentId;

    /****************
     * Functions**
     *****************/

    @Override
    public String getProjectIdentificator(){
        return String.valueOf(this.projectId);
    }

    /*******************
     * Getter & Setters**
     *****************/


    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getParentIssueId() {
        return parentIssueId;
    }

    public void setParentIssueId(Integer parentIssueId) {
        this.parentIssueId = parentIssueId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getEstimatedHours() {
        return estimatedHours;
    }

    public void setEstimatedHours(Integer estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    public Integer getActualHours() {
        return actualHours;
    }

    public void setActualHours(Integer actualHours) {
        this.actualHours = actualHours;
    }

    public Integer getIssueTypeId() {
        return issueTypeId;
    }

    public void setIssueTypeId(Integer issueTypeId) {
        this.issueTypeId = issueTypeId;
    }

    public Integer[] getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer[] categoryId) {
        this.categoryId = categoryId;
    }

    public Integer[] getVersionId() {
        return versionId;
    }

    public void setVersionId(Integer[] versionId) {
        this.versionId = versionId;
    }

    public Integer[] getMilestoneId() {
        return milestoneId;
    }

    public void setMilestoneId(Integer[] milestoneId) {
        this.milestoneId = milestoneId;
    }

    public Integer getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(Integer priorityId) {
        this.priorityId = priorityId;
    }

    public Integer getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(Integer assigneeId) {
        this.assigneeId = assigneeId;
    }

    public Integer[] getNotifiedUserId() {
        return notifiedUserId;
    }

    public void setNotifiedUserId(Integer[] notifiedUserId) {
        this.notifiedUserId = notifiedUserId;
    }

    public Integer[] getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Integer[] attachmentId) {
        this.attachmentId = attachmentId;
    }
}
