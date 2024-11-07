package com.eheiste.laureatnet.security;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    private static final String[] WHITE_LIST_URL = {
            "/api/v1/files/**",
            "/api/v1/stomp-endpoint/**",
            "/api/v1/auth/**",
            "swagger-ui/index.html",
    };

    private static final String[] LAURET_URL = {
            "/api/v1/post/internship-offers"
    };

    private static final String[] ADMIN_URL = {
            "/api/v1/accounts/users",
            "/api/v1/accounts/import",
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        logger.info("Configuring security filter chain");

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req
                        .requestMatchers(WHITE_LIST_URL)
                        .permitAll()
                        .requestMatchers(HttpMethod.POST, LAURET_URL)
                        .hasRole("LAURET")
                        .requestMatchers(HttpMethod.PUT, LAURET_URL)
                        .hasRole("LAURET")
                        .requestMatchers(HttpMethod.DELETE, LAURET_URL)
                        .hasRole("LAURET")
                        .requestMatchers(HttpMethod.PATCH, LAURET_URL)
                        .hasRole("LAURET")

                        .requestMatchers(ADMIN_URL)
                        .hasRole("ADMIN")

                        .anyRequest()
                        .authenticated()

                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/api/v1/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                );

        return http.build();
    }
}