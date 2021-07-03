package tools.mtsuite.core.core.keycloakSync.utils;


import org.keycloak.admin.client.Keycloak;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tools.mtsuite.core.core.keycloakSync.dto.AllEntityDto;
import tools.mtsuite.core.core.keycloakSync.dto.ModifyEntityDto;

@Component
public class KeycloakConnectionSync {

    @Value("${keycloak.protocol}://${keycloak.url}:${keycloak.port}/auth/")
    private String serverUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.clientId}")
    private String clientId;

    @Value("${keycloak.username}")
    private String username;

    @Value("${keycloak.password}")
    private String password;

    @Value("${keycloak.clientSecret}")
    private String clientSecret;

    @Autowired
    private RestTemplate httpClient;

    @Value("${keycloak.protocol}://${keycloak.url}:${keycloak.port}/auth/realms/${keycloak.realm}/customApi/")
    private String apiUrl;

    private Keycloak kc;

    // Log
    private static final Logger log = LoggerFactory.getLogger(KeycloakConnectionSync.class);

    public KeycloakConnectionSync() {
    }

    /**
     * Get full data from keycloak
     * @return
    */
    public AllEntityDto getFullData() {
        ResponseEntity<AllEntityDto> response =  httpClient.getForEntity(apiUrl + "getAllEntities",AllEntityDto.class,generateHttpHeader());
        return response.getBody();
    }


    public ModifyEntityDto getModifyEntities(String sinceDate) {
        ResponseEntity<ModifyEntityDto> response =  httpClient.getForEntity(apiUrl + "modifyEntities/"+sinceDate,ModifyEntityDto.class,generateHttpHeader());
        return response.getBody();
    }


    private HttpHeaders generateHttpHeader(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", this.getToken());
        return  headers;
    }


    private String getToken() {
        if(kc == null)
        {
            //kc = Keycloak.getInstance(serverUrl,  realm,  clientId,  authToken);
            log.info("Create instance");
            kc = this.getKeycloakInstance();
        }

        // TODO: just for test
        if(kc.isClosed()) {
            log.info("Is close");
            kc = this.getKeycloakInstance();
        }

        return kc.tokenManager().getAccessTokenString();
    }

    private Keycloak getKeycloakInstance() {
        return Keycloak.getInstance( this.serverUrl,  this.realm,  this.username,  this.password,  this.clientId,  this.clientSecret);
    }




}
