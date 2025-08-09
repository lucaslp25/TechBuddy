package com.lpdev.techbuddy.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "TechBuddy API",
                version = "1.0.0",
                description = "API para a plataforma de mentoria TechBuddy, conectando desenvolvedores e mentores."
        )
)
public class OpenApiConfig {
}