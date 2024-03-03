package com.gitlab.emradbuba.learning.learningproject.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class SwaggerConfig {

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
                );
    }
}
