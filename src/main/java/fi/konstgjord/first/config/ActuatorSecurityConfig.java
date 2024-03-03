package fi.konstgjord.first.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class ActuatorSecurityConfig {
    @Bean
    public SecurityFilterChain actuatorSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/actuator/health").permitAll() // Allow access to the health endpoint without authentication
                                .requestMatchers("/actuator/**").hasRole("ACTUATOR_ADMIN") // Require ACTUATOR_ADMIN role for other Actuator endpoints
                                .anyRequest().permitAll() // Require authentication for all other requests
                )
                .httpBasic(withDefaults())
                // Configure CSRF
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/cats/**") // Disable CSRF for this specific endpoint
                ); // Use HTTP Basic Authentication


        return http.build();
    }
}