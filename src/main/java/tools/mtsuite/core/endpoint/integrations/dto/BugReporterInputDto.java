package tools.mtsuite.core.endpoint.integrations.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import tools.mtsuite.core.common.integrations.BugReporterType;
import tools.mtsuite.core.core.dto.GenericObjectDto;
import tools.mtsuite.core.endpoint.integrations.dto.backlog.BackLogInputDto;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ @JsonSubTypes.Type(value = BackLogInputDto.class, name = "Backlog")})
public abstract  class BugReporterInputDto extends GenericObjectDto {

    /****************
     * Attributes **
     *****************/

    protected BugReporterType type;


    /****************
     * Functions **
     *****************/

    public abstract String getProjectIdentificator();

    /****************
     * Getter&Setter **
     *****************/

    public BugReporterInputDto() {
    }

    public BugReporterType getType() {
        return type;
    }

    public void setType(BugReporterType type) {
        this.type = type;
    }
}
