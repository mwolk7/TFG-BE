package tools.mtsuite.core.core.kc.security;

import org.keycloak.representations.AccessToken;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Arrays;


public class KcAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = -1949976839306453197L;

    private String username;
    private String userRolId;

    public KcAuthenticationToken(){
        super(Arrays.asList());
        super.setAuthenticated(false);
    }
    public KcAuthenticationToken(AccessToken token){
        super(Arrays.asList());

        this.username = token.getPreferredUsername();
        this.userRolId = (String) token.getOtherClaims().get("userRolId");
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.userRolId;
    }

    @Override
    public Object getPrincipal() {
        return this.username;
    }
}
