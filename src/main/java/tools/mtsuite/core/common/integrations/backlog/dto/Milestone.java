package tools.mtsuite.core.common.integrations.backlog.dto;

public class Milestone {

    private Long id;
    private Long projectId;
    private String name;
    private String description;
    private String startDate;
    private String releaseDueDate;
    private Boolean archived;
    private Integer displayOrder;


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

    public String getReleaseDueDate() {
        return releaseDueDate;
    }

    public void setReleaseDueDate(String releaseDueDate) {
        this.releaseDueDate = releaseDueDate;
    }

    public Boolean getArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }
}
