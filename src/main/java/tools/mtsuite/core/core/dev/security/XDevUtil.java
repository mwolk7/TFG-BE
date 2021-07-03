package tools.mtsuite.core.core.dev.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*")
public class XDevUtil{

    public static Authentication validateToken(Authentication authentication) throws AuthenticationException {

        XDevAuthenticationToken xDevAuthenticationToken = (XDevAuthenticationToken) authentication;
        if (xDevAuthenticationToken.getToken() == null) {

        }
        SecurityContextHolder.getContext().setAuthentication(xDevAuthenticationToken);
        return  xDevAuthenticationToken;

    }

}
