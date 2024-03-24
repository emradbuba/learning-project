package com.gitlab.emradbuba.learning.learningproject.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfigSwagger {

    @Bean
    @Order(400)
    public SecurityFilterChain swaggerSecurityConfiguration(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .securityMatcher("/api-docs/**", "/swagger-ui/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(antMatcher("/api-docs/**")).permitAll()
                        .requestMatchers(antMatcher("/swagger-ui/**")).permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(managementConfig -> managementConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers(headers -> headers.frameOptions(Customizer.withDefaults()).disable())
                .build();
    }

}
