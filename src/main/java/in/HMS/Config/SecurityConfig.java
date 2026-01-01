package in.HMS.Config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import in.HMS.IService.CustomUserDetailsService;
import in.HMS.Utils.JwtFilter;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;



@Configuration
public class SecurityConfig {

	private final JwtFilter jwtAuthenticationFilter;
	private final CustomUserDetailsService customUserDetailsService;

	public SecurityConfig(JwtFilter jwtAuthenticationFilter,
			CustomUserDetailsService customUserDetailsService) {
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
		this.customUserDetailsService = customUserDetailsService;
	}

	/**
	 * MAIN SECURITY FILTER CHAIN
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http
				// Disable CSRF (JWT based stateless auth)
				.csrf(csrf -> csrf.disable())

				// Enable CORS
				.cors(Customizer.withDefaults())

				// Stateless session
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				// Authorization rules
				.authorizeHttpRequests(auth -> auth
						// Swagger
						.requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll()
						.requestMatchers(// Auth endpoints
								"/api/admin/auth/**", "/api/doctor/auth/**", "/api/patient/auth/**")
						.permitAll() // Role based APIs
						.requestMatchers("/api/admin/**").hasRole("ADMIN").requestMatchers("/api/doctor/**")
						.hasRole("DOCTOR").requestMatchers("/api/patient/**").hasRole("PATIENT")

						// Any other request
						.anyRequest().authenticated())

				// Authentication provider
				.authenticationProvider(authenticationProvider())

				// JWT filter
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	/**
	 * PASSWORD ENCODER
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * AUTHENTICATION PROVIDER
	 */
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(customUserDetailsService);
//		provider.setUserDetailsService(customUserDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}

	/**
	 * AUTHENTICATION MANAGER
	 */
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	/**
	 * CORS CONFIGURATION
	 */
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {

		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
		config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
		config.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);

		return source;
	}
	
    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
            .info(new Info()
                .title("Hospital Management System API")
                .version("1.0")
                .description("API documentation for HMS"))
            .addSecurityItem(
                new SecurityRequirement().addList("Bearer Authentication")
            )
            .components(
                new Components().addSecuritySchemes(
                    "Bearer Authentication",
                    new SecurityScheme()
                        .name("Authorization")
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                )
            );
    }
}
