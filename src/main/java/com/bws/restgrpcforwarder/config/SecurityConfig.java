package com.bws.restgrpcforwarder.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.bws.restgrpcforwarder.auth.ApiKeyAuthFilter;

/**
 * Configuration class for security settings.
 * This class sets up the security configuration for API key authentication in the application.
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * The name of the header containing the API key.
     */
    @Value("${api.key.header.name}")
    private String authTokenHeaderName;

    /**
     * The API key used for authentication.
     */
    @Value("${api.key}")
    private String authToken;

    /**
     * Configures the security filter chain for API key authentication.
     *
     * @param http the HttpSecurity configuration
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {
        http
        .csrf((csrf) -> csrf.disable())
        .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/livenessdetection", "/photoverify",
                    "/videolivenessdetection").authenticated())
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new ApiKeyAuthFilter(authTokenHeaderName,authToken), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
