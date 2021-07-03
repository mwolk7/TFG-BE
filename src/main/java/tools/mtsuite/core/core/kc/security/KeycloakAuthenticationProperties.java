package tools.mtsuite.core.core.kc.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class KeycloakAuthenticationProperties {

    @Value("${app.security.keycloak.audience:account}")
    private String audience;

    @Value("${app.security.keycloak.client:fe-not-bug}")
    private String client;

    @Value("${app.security.keycloak.certificate}")
    private String certificate;

    @Value("${app.security.keycloak.skew:30s}")
    private Duration skew;

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public Duration getSkew() {
        return skew;
    }

    public void setSkew(Duration skew) {
        this.skew = skew;
    }
}
