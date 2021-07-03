package tools.mtsuite.core.core.prod.security;

import io.jsonwebtoken.Jwts;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@ConditionalOnProperty(value = "app.security.mode", havingValue = "auth-db")
public class JwtFilter extends GenericFilterBean {
	static final String ORIGIN = "Origin";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {

		// Obtenemos el token que viene en el encabezado de la peticion
		String token = ((HttpServletRequest) request).getHeader("Authorization");
		String[] list_roles = { null };
		if (token != null) {

			String username = Jwts.parser().setSigningKey(JwtUtil.KEYSECRET) // la clave secreta esta declara en JwtUtil
					.parseClaimsJws(token) // este metodo es el que valida
					.getBody().getSubject();
		}

		Authentication authentication = JwtUtil.getAuthentication((HttpServletRequest) request, list_roles);
		if(authentication != null) {
		
				String username = authentication.getPrincipal().toString();
		
				ServletContext servletContext = request.getServletContext();
				WebApplicationContext webApplicationContext = WebApplicationContextUtils
						.getWebApplicationContext(servletContext);
				//UserService userService = webApplicationContext.getBean(UserService.class);
				String[] rol = { null };
				authentication = JwtUtil.getAuthentication((HttpServletRequest) request, rol);
		}

		// agrega authorizacion al contexto
		SecurityContextHolder.getContext().setAuthentication(authentication);

		filterChain.doFilter(request, response);
	}

}
