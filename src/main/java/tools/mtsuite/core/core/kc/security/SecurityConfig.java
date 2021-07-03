package tools.mtsuite.core.core.kc.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@ConditionalOnProperty(value = "app.security.mode", havingValue = "auth-kc-prod")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.cors()
				.and()
				.csrf().disable()
				.authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers("/login").permitAll()
				.antMatchers("/kc/**").permitAll()
				.antMatchers("/v3/**").permitAll()
				.antMatchers("/file/token/integration/**").permitAll()
				.antMatchers("/file/content/**").permitAll()
				.anyRequest().authenticated()
				.and().addFilterBefore(new KeycloakFilter(), BasicAuthenticationFilter.class);
	}


}
