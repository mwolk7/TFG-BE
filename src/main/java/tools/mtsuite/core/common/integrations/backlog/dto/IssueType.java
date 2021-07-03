package tools.mtsuite.core.common.integrations.backlog.dto;

public class IssueType {

    private Long id;
    private Long projectId;
    private String  name;
    private String  color;
    private String  templateSummary;
    private String  templateDescription;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTemplateSummary() {
        return templateSummary;
    }

    public void setTemplateSummary(String templateSummary) {
        this.templateSummary = templateSummary;
    }

    public String getTemplateDescription() {
        return templateDescription;
    }

    public void setTemplateDescription(String templateDescription) {
        this.templateDescription = templateDescription;
    }
}
