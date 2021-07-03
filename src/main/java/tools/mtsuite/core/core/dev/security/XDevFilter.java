package tools.mtsuite.core.core.dev.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@ConditionalOnProperty(value = "app.security.mode", havingValue = "auth-x-dev")
public class XDevFilter extends OncePerRequestFilter {
	static final String ORIGIN = "Origin";

	@Override
	protected void doFilterInternal(HttpServletRequest request,
									HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// Obtenemos el token que viene en el encabezado de la peticion
		String token = request.getHeader("X-Dev-Authorization");

		if (token != null) {
			XDevAuthenticationToken auth = new XDevAuthenticationToken(token);
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
		// if token is missing -> continue checking against the white list URI
		filterChain.doFilter(request, response);


	}


}
