package tools.mtsuite.core.core.interceptor.access;

public enum PermissionAction {
    Create("c"),
    Read("r"),
    Update("u"),
    Delete("d"),
    Sync("s"),
    None(""),
    CreateAndUpdate("c.u"),
    Crud("c.r.u.d");

    private String action;

    private PermissionAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}

