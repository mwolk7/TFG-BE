package tools.mtsuite.core.core.context;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CORSFilter implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*")
		.allowedMethods("GET", "PUT", "POST", "DELETE", "OPTIONS")
		.allowedHeaders( "*")
		.exposedHeaders("Authorization")
		.maxAge(3600)
		.allowCredentials(true);
	}
}
