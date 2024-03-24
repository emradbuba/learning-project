package com.gitlab.emradbuba.learning.learningproject.config.security.memory;

import com.gitlab.emradbuba.learning.learningproject.config.security.SecurityConfigActuator;
import com.gitlab.emradbuba.learning.learningproject.config.security.SecurityConfigRestApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class InMemoryUsersConfig {

    private static final String PASSWORD_BCRYPTED = "{bcrypt}$2a$04$miXxzUlDyDV4Wwmn1IKpt.pSLEYaTBptJHeyJRjxZJrPyX2Is5cXS";

    @Bean("inMemoryUserDetailsService")
    public UserDetailsService inMemoryUserDetailsService() {
        UserDetails userDetails = User.builder()
                .username("user")
                .password(PASSWORD_BCRYPTED)
                .roles(SecurityConfigRestApi.USER)
                .build();

        UserDetails adminDetails = User.builder()
                .username("admin")
                .password(PASSWORD_BCRYPTED)
                .roles(SecurityConfigRestApi.ADMIN)
                .build();

        UserDetails devopsUserDetails = User.builder()
                .username("devopsuser")
                .password(PASSWORD_BCRYPTED)
                .roles(SecurityConfigActuator.DEVOPS_USER)
                .build();

        UserDetails devopsAdminDetails = User.builder()
                .username("devopsadmin")
                .password(PASSWORD_BCRYPTED)
                .roles(SecurityConfigActuator.DEVOPS_ADMIN)
                .build();

        return new InMemoryUserDetailsManager(userDetails, adminDetails, devopsAdminDetails, devopsUserDetails);
    }
}
