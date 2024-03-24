package com.gitlab.emradbuba.learning.learningproject.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String BASIC_AUTH_SCHEME_NAME = "basicAuth";
    private static final String BASIC_AUTH_SCHEME = "basic";

    @Value("${app.version:unknown}")
    private String projectVersion;

    @Bean
    public OpenAPI defineOpenApi() {
        return new OpenAPI()
                .openapi("3.0.3")
                .info(new Info()
                        .title("LearningApplication API for training purposes")
                        .summary("This is a sample summary of the exposed API")
                        .description("Main goal is to simulate an API for storing persons and extra info about them...")
                        .version("LearningProject: " + projectVersion)
                )
                .externalDocs(new ExternalDocumentation()
                        .description("Official OpenAPI documentation")
                        .url("https://swagger.io/specification/")
                )
                .components(new Components()
                        .addSecuritySchemes(BASIC_AUTH_SCHEME_NAME, createSecurityScheme())
                )
                .addSecurityItem(new SecurityRequirement()
                        .addList(BASIC_AUTH_SCHEME_NAME));
    }

    private SecurityScheme createSecurityScheme() {
        return new SecurityScheme()
                .name(BASIC_AUTH_SCHEME_NAME)
                .type(SecurityScheme.Type.HTTP)
                .in(SecurityScheme.In.HEADER)
                .scheme(BASIC_AUTH_SCHEME);
    }
}
