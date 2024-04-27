package com.gitlab.emradbuba.learning.learningproject.config.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

import static com.gitlab.emradbuba.learning.learningproject.libs.whitelist.GodModeLoginsProvider.GOD_MODE_ROLENAME;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfigRestApi {

    public static final String ADMIN_ROLE_NAME = "ADMIN";
    public static final String USER_ROLE_NAME = "USER";
    public static final String API_URL_PATTERN = "/api/**";

    private final UserDetailsService inMemoryUsersDetailsService;

    public SecurityConfigRestApi(@Qualifier("inMemoryUserDetailsService") UserDetailsService userDetailsService) {
        this.inMemoryUsersDetailsService = userDetailsService;
    }

    @Bean
    @Order(500)
    public SecurityFilterChain restApiSecurityConfiguration(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .securityMatcher(API_URL_PATTERN)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(antMatcher(HttpMethod.DELETE, API_URL_PATTERN)).hasAnyRole(ADMIN_ROLE_NAME, GOD_MODE_ROLENAME)
                        .requestMatchers(antMatcher(API_URL_PATTERN)).hasAnyRole(USER_ROLE_NAME, ADMIN_ROLE_NAME, GOD_MODE_ROLENAME)
                        .anyRequest().authenticated()
                )
                .userDetailsService(inMemoryUsersDetailsService)
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}

