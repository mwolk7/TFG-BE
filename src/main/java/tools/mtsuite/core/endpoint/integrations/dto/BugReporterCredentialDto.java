package tools.mtsuite.core.endpoint.integrations.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import tools.mtsuite.core.common.integrations.BugReporterType;
import tools.mtsuite.core.common.model.integrations.BugReporterCredential;
import tools.mtsuite.core.core.dto.GenericObjectDto;
import tools.mtsuite.core.endpoint.integrations.dto.backlog.BackLogCredentialDto;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ @JsonSubTypes.Type(value = BackLogCredentialDto.class, name = "Backlog")})
public abstract  class BugReporterCredentialDto  extends GenericObjectDto {


    private Long userId;
    private String name;

    protected BugReporterType type;

    public BugReporterCredentialDto() {
    }

    public abstract BugReporterCredential modelInstance();


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BugReporterType getType() {
        return type;
    }

    public void setType(BugReporterType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
