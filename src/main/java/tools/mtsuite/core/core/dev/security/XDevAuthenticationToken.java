package tools.mtsuite.core.core.dev.security;
import java.util.Arrays;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import scala.util.control.Exception;
import tools.mtsuite.core.core.dto.LoginUserDto;


public class XDevAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = -1949976839306453197L;

    private final String token;
    private final LoginUserDto user = new LoginUserDto();


    public XDevAuthenticationToken(String token){
        super(Arrays.asList());


        String[] tokenParser = { null };
        String[] list_roles  = { null };
        String[] list_group  = { null };
        String name = "";
        String username = "";

        try {
            tokenParser = token.split(";");
            username = tokenParser[0];
            name = tokenParser[1];
            list_roles = tokenParser[2].split(",");
            list_group = tokenParser[3].split(",");

            this.token = token;
            this.user.setUsername(username);
            this.user.setName(name);
            this.user.setRoles(list_roles);
            this.user.setGroups(list_group);

            super.setAuthenticated(true);

        }catch (Throwable e){
            throw new BadCredentialsException("Bad credential format." + e.getMessage());
        }
    }

    @Override
    public Object getCredentials() {
        return this.user.getRoles();
    }

    @Override
    public Object getPrincipal() {
        return this.user.getUsername();
    }

    public String getToken(){
        return this.token;
    }
}
