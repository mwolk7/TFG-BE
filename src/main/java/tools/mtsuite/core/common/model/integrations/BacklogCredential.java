package tools.mtsuite.core.common.model.integrations;

import tools.mtsuite.core.common.integrations.BugReporterType;
import tools.mtsuite.core.core.utils.AppConstants;
import tools.mtsuite.core.core.utils.DateParser;
import tools.mtsuite.core.endpoint.integrations.dto.BugReporterCredentialDto;
import tools.mtsuite.core.endpoint.integrations.dto.backlog.BackLogCredentialDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = AppConstants.BACKLOG_CREDENTIAL)
public class BacklogCredential extends BugReporterCredential {
    /****************
     * Relationships**
     *****************/


    /****************
     * Attributes **
     *****************/
    @Column(name = "URL")
    private String url;
    @Column(name = "API_KEY")
    private String apiKey;


    /*****************
     * Functions *****
     *****************/

    public BacklogCredential() {
    }

    @Override
    public BugReporterCredentialDto getDto() {
        BackLogCredentialDto backLogCredentialDto = new BackLogCredentialDto();
        backLogCredentialDto.setId(this.getId());
        backLogCredentialDto.setCreatedDate(DateParser.dateToString(this.getCreatedDate()));
        backLogCredentialDto.setLastModifiedDate(DateParser.dateToString(this.getLastModifiedDate()));
        backLogCredentialDto.setApiKey(this.apiKey);
        backLogCredentialDto.setType(this.getType());
        backLogCredentialDto.setUrl(this.url);
        backLogCredentialDto.setUserId(this.getUser().getId());
        backLogCredentialDto.setName(this.getName());
        return backLogCredentialDto;
    }

    @Override
    public void update(BugReporterCredentialDto bugReporterCredentialDto) {
        BackLogCredentialDto backLogCredentialDto = (BackLogCredentialDto) bugReporterCredentialDto;
        if(this.url != null)
            this.url    = backLogCredentialDto.getUrl();
        if(this.apiKey != null)
            this.apiKey = backLogCredentialDto.getApiKey();
        if(this.getName() != null)
            this.setName(backLogCredentialDto.getName());
    }

    @Override
    public BugReporterType getType() {
        return BugReporterType.Backlog;
    }

    /*****************
     * Getter & Setters**
     *****************/
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }


}
