package com.gitlab.emradbuba.learning.learningproject.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import static com.gitlab.emradbuba.learning.learningproject.config.security.SecurityConfigRestApi.ADMIN_ROLE_NAME;
import static com.gitlab.emradbuba.learning.learningproject.config.security.SecurityConfigRestApi.USER_ROLE_NAME;
import static com.gitlab.emradbuba.learning.learningproject.libs.whitelist.GodModeLoginsProvider.GOD_MODE_ROLENAME;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfigActuator {

    public static final String DEVOPS_USER = "DEVOPS_USER";
    public static final String DEVOPS_ADMIN = "DEVOPS_ADMIN";

    @Bean
    @Order(200)
    public SecurityFilterChain actuatorSecurityConfiguration(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .securityMatcher(antMatcher("/actuator/**"))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(matcherRegistry -> matcherRegistry
                        .requestMatchers(antMatcher("/actuator/health/**")).hasAnyRole(USER_ROLE_NAME, ADMIN_ROLE_NAME, GOD_MODE_ROLENAME)
                        .requestMatchers(antMatcher("/actuator/appinfo/**")).hasAnyRole(ADMIN_ROLE_NAME, GOD_MODE_ROLENAME)
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}
