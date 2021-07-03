package tools.mtsuite.core.core.prod.security;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.CrossOrigin;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
@CrossOrigin(origins = "*")
public class JwtUtil {
	
	public static String KEYSECRET = "gypseewifi2018";
	
    // Metodo para crear el JWT y enviarlo al cliente en el header de la respuesta
	@CrossOrigin(origins = "*")

    static void addAuthentication(HttpServletResponse res, String username) throws IOException {

		  Map<String, Object> additionalInfo = new HashMap<>();

         String token = Jwts.builder()
            .setSubject(username)
            // Vamos a asignar un tiempo de expiracion de 12 horas
            .setExpiration(new Date(System.currentTimeMillis() + 43200000))
            // Hash con el que firmaremos la clave
            .signWith(SignatureAlgorithm.HS512, KEYSECRET)
            .compact();
        //agregamos al encabezado el token
        res.addHeader("Authorization", token);
        res.getWriter().print(token);
    }

    // Metodo para validar el token enviado por el cliente
	@CrossOrigin(origins = "*")
	public
    static Authentication getAuthentication(HttpServletRequest request, String[] roles) throws IOException {
    	   	    	    	    	
        // Obtenemos el token que viene en el encabezado de la peticion
        String token = request.getHeader("Authorization");
        // si hay un token presente, entonces lo validamos
        if (token != null) {
            String user = Jwts.parser()
                    .setSigningKey(KEYSECRET)
                    .parseClaimsJws(token) //este metodo es el que valida
                    .getBody()
                    .getSubject();
        
            // Recordar que para las demas peticiones que no sean /login
            // no requerimos una autenticacion por username/password
            // por este motivo podemos devolver un UsernamePasswordAuthenticationToken sin password, pero debemos consultar nuevamente los roles
            // seguramente hay una mejor forma de implementar roles, pero no esta del todo mal volver a consultarlos, puesto que si hubo un cambio
            // en la asignación de roles a un usuario, se le otorgara o se le denegara el acceso de forma automatica, y no hay que esperar al vencimiento del token
       
            
            return user != null ?
                    new UsernamePasswordAuthenticationToken(user, null, AuthorityUtils.createAuthorityList(roles)) :
                    null;
                
        }
        
        return null;
    }
    
}
