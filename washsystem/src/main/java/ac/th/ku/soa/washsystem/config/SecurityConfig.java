package ac.th.ku.soa.washsystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ac.th.ku.soa.washsystem.security.JwtFilter;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private JwtFilter jwtFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.authorizeHttpRequests(authorizeRequests -> authorizeRequests
						.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
						.requestMatchers("/public/**").permitAll()
						.requestMatchers("/emp/**").hasRole("EMPLOYEE")
						.anyRequest().authenticated()

				).exceptionHandling()
	            .authenticationEntryPoint((request, response, authException) -> {
	                // Custom JSON response for 401 Unauthorized
	                response.setContentType("application/json");
	                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	                response.getWriter().write("{\"code\":\"401\", \"msg\": \"Unauthorized - Token required\"}");
	            })
	            .accessDeniedHandler((request, response, accessDeniedException) -> {
	                // Custom JSON response for 403 Forbidden
	                response.setContentType("application/json");
	                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
	                response.getWriter().write("{\"code\":\"403\", \"msg\": \"Forbidden - Insufficient role\"}");
	            })
				.and().addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
