package tools.mtsuite.core.core.prod.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.CrossOrigin;
import tools.mtsuite.core.core.dto.LoginUserDto;
import tools.mtsuite.core.common.model.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;


@CrossOrigin(origins = "*")
@ConditionalOnProperty(value = "app.security.mode", havingValue = "auth-db")
public class LoginFilter extends AbstractAuthenticationProcessingFilter {

	public LoginFilter(String url, AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authManager);

	}

	static final String ORIGIN = "Origin";

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException, IOException, ServletException {

		InputStream body = req.getInputStream();
		LoginUserDto userDto = new ObjectMapper().readValue(body, LoginUserDto.class);
		return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(userDto.getUsername(),
				userDto.getPassword(), Collections.emptyList()));

	}

	public Authentication attemptAuthentication2(HttpServletRequest req, HttpServletResponse res, User user)
			throws AuthenticationException, IOException, ServletException {
		return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),
				user.getPassword(), Collections.emptyList()));
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET,  DELETE, PUT");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers",
				"Accept, Content-Type, Origin, Authorization, X-Auth-Token,access-control-allow-origin");
		throw new UsernameNotFoundException("Invalid username or password.");

	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {

		HttpServletResponse response = res;
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Expose-Headers", "Authorization");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET,  DELETE, PUT");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "Accept, Content-Type, Origin, Authorization, X-Auth-Token");
		JwtUtil.addAuthentication(response, auth.getName());
	}
}
