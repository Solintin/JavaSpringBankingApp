package org.bankapp.bankingapp.config.security;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider userAuthenticationProvider;


    // Manually create a constructor
    public SecurityConfiguration(
            JwtAuthFilter jwtAuthFilter,
            @Qualifier("userAuthenticationProvider") AuthenticationProvider userAuthenticationProvider)
    {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userAuthenticationProvider = userAuthenticationProvider;
    }

    public static final String[] WHITE_LIST_URL = {
            "/sms-callback",
            "http://localhost:3000",
            "/api/v1/patient/login",
            "/api/v1/auth/**",
            "/api/v1/utils/**",
            "/api/v1/hospital/**",
            "/api/v1/staff/**",
            "/api/v1/patient/**",
            "/api/v1/admin/**",
            "/swagger-ui/**",
            "/v3/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/webjars/**",
            "/swagger-ui.html",
            "/forgotPassword/**",
            "/api/v1/patient/forgot-password"
    };

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000","https://secsystem-emr.vercel.app", "Postman"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true); // Allow cookies or Authorization headers

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(c -> c.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(WHITE_LIST_URL).permitAll()
                        .requestMatchers(request -> {
                            String remoteAddr = request.getRemoteAddr();
                            return "127.0.0.1".equals(remoteAddr) || "localhost".equals(request.getServerName());
                        }).permitAll()
                        .anyRequest()
                        .authenticated())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(userAuthenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}