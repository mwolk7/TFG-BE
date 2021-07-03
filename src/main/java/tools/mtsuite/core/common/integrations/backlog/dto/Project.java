package tools.mtsuite.core.common.integrations.backlog.dto;

public class Project {

    private Long id;
    private String  projectKey;
    private String  name;
    private Boolean  chartEnabled;
    private Boolean  subtaskingEnabled;
    private Boolean projectLeaderCanEditProjectLeader  ;
    private Boolean useWikiTreeView  ;
    private String textFormattingRule;
    private Boolean archived   ;
    private Long displayOrder  ;
    private Boolean useDevAttributes  ;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectKey() {
        return projectKey;
    }

    public void setProjectKey(String projectKey) {
        this.projectKey = projectKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getChartEnabled() {
        return chartEnabled;
    }

    public void setChartEnabled(Boolean chartEnabled) {
        this.chartEnabled = chartEnabled;
    }

    public Boolean getSubtaskingEnabled() {
        return subtaskingEnabled;
    }

    public void setSubtaskingEnabled(Boolean subtaskingEnabled) {
        this.subtaskingEnabled = subtaskingEnabled;
    }

    public Boolean getProjectLeaderCanEditProjectLeader() {
        return projectLeaderCanEditProjectLeader;
    }

    public void setProjectLeaderCanEditProjectLeader(Boolean projectLeaderCanEditProjectLeader) {
        this.projectLeaderCanEditProjectLeader = projectLeaderCanEditProjectLeader;
    }

    public Boolean getUseWikiTreeView() {
        return useWikiTreeView;
    }

    public void setUseWikiTreeView(Boolean useWikiTreeView) {
        this.useWikiTreeView = useWikiTreeView;
    }

    public String getTextFormattingRule() {
        return textFormattingRule;
    }

    public void setTextFormattingRule(String textFormattingRule) {
        this.textFormattingRule = textFormattingRule;
    }

    public Boolean getArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
    }

    public Long getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Long displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Boolean getUseDevAttributes() {
        return useDevAttributes;
    }

    public void setUseDevAttributes(Boolean useDevAttributes) {
        this.useDevAttributes = useDevAttributes;
    }
}
