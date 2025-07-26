package com.example.authproject.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Set;

@Configuration
@OpenAPIDefinition(
    info = @Info(title = "Auth API", version = "v1")
)
@SecurityScheme(
    name = "BearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT"
)
public class SwaggerConfig {

    private static final Set<String> EXCLUDED_PATHS = Set.of(
        "/api/login", "/api/signup"
    );

    @Bean
    public OpenApiCustomizer globalSecurityCustomizer() {
        return openApi -> {
            // 전체 경로 순회
            openApi.getPaths().forEach((path, pathItem) -> {
                if (!EXCLUDED_PATHS.contains(path)) {
                    // GET, POST, PUT, DELETE 등 모든 메서드별로 적용
                    pathItem.readOperations().forEach(operation -> {
                        applySecurity(operation);
                    });
                }
            });
        };
    }

    private void applySecurity(Operation operation) {
        SecurityRequirement requirement = new SecurityRequirement().addList("BearerAuth");
        operation.addSecurityItem(requirement);
    }
}
