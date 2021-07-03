package tools.mtsuite.core.common.integrations.backlog;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tools.mtsuite.core.common.model.integrations.BacklogCredential;

@Component
public class BacklogUrlBuilder {

    @Value("${integrations.backlog.url.context-path}")
    private String contextPath;
    @Value("${integrations.backlog.url.version}")
    private String version;

    //https://fgonzalez.backlog.com/api/v2/users/myself?apiKey=i5cNhTMNvAFHnX2Oo3CPziE4pnNFwwZ4Jd220mMk8NqeaJaWbeuGSKJiiYtEnebt
    public String generateUrl(BacklogCredential backlogCredential,String endpoint){
        StringBuilder apiUrl = new StringBuilder();
        apiUrl.append(backlogCredential.getUrl());
        apiUrl.append(contextPath);
        apiUrl.append("/");
        apiUrl.append(version);
        apiUrl.append("/");
        apiUrl.append(endpoint);
        apiUrl.append("?apiKey=");
        apiUrl.append(backlogCredential.getApiKey());
        return apiUrl.toString();
    }

}
