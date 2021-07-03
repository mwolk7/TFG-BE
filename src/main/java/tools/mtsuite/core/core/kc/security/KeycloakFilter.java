package tools.mtsuite.core.core.kc.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@ConditionalOnProperty(value = "app.security.mode", havingValue = "auth-kc-prod")
public class KeycloakFilter extends OncePerRequestFilter {
	static final String ORIGIN = "Origin";

 	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// Obtain authorization token from the Header

		String token = request.getHeader("Authorization");

		if (token != null) {
			ServletContext servletContext = request.getServletContext();
			WebApplicationContext webApplicationContext = WebApplicationContextUtils
					.getWebApplicationContext(servletContext);
			KeycloakBearerTokenReader keycloakBearerTokenReader = webApplicationContext.getBean(KeycloakBearerTokenReader.class);
			Authentication auth = keycloakBearerTokenReader.tokenValidation(token);
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
		// if token is missing -> continue checking against the white list URI
		filterChain.doFilter(request, response);


	}


}
