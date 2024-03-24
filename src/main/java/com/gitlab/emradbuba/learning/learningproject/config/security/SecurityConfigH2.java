package com.gitlab.emradbuba.learning.learningproject.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfigH2 {

    public static final String H2_CONSOLE_PATTERN = "/h2-console/**";

    @Bean
    @Order(300)
    public SecurityFilterChain h2SecurityConfiguration(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .securityMatcher(antMatcher(H2_CONSOLE_PATTERN))
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(antMatcher(H2_CONSOLE_PATTERN)).permitAll();
                    auth.anyRequest().authenticated();
                })
                .csrf(csrf -> csrf.ignoringRequestMatchers(antMatcher(H2_CONSOLE_PATTERN)))
                .headers(headers -> headers.frameOptions(Customizer.withDefaults()).disable())
                .build();
    }
}
