package tools.mtsuite.core.endpoint.integrations.dto.backlog;

import tools.mtsuite.core.common.model.GenericObject;
import tools.mtsuite.core.common.model.integrations.BacklogCredential;
import tools.mtsuite.core.common.model.integrations.BugReporterCredential;
import tools.mtsuite.core.endpoint.integrations.dto.BugReporterCredentialDto;

public class BackLogCredentialDto extends BugReporterCredentialDto {


    /****************
     * Attributes**
     *****************/
    private String url;
    private String apiKey;

    /****************
     * Functions**
     *****************/

    @Override
    public BugReporterCredential modelInstance() {
        BacklogCredential bugReporterCredential = new BacklogCredential();

        if(this.url != null)
            bugReporterCredential.setUrl(this.url);

        if(this.apiKey != null)
            bugReporterCredential.setApiKey(this.apiKey);

        if(this.getName() != null)
            bugReporterCredential.setName(this.getName());
        
        return bugReporterCredential;
    }

    /*******************
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
