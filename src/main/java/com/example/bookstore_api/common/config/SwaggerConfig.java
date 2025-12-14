package com.example.bookstore_api.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;
import java.util.Comparator;
import java.util.List;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI bookstoreOpenAPI() {
        return new OpenAPI()
            .info(
                new Info()
                    .title("BookStore-API")
                    .description("Spring Boot 기반 도서 관리 백엔드 API (JWT 인증, Soft Delete 적용)")
                    .version("1.0.0")
            )
            .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
            .components(new Components().addSecuritySchemes(SECURITY_SCHEME_NAME, bearerScheme()));
    }

    private SecurityScheme bearerScheme() {
        return new SecurityScheme()
            .name(SECURITY_SCHEME_NAME)
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")
            .description("Authorization 헤더에 Bearer {AccessToken} 형태로 전달합니다.");
    }

    @Bean
    public OpenApiCustomizer tagOrderCustomizer() {
        List<String> priority = List.of("Users", "Books", "Reviews", "Comments", "Library", "Wishlist");
        return openApi -> {
            if (openApi.getTags() == null) {
                return;
            }
            openApi.getTags().sort(
                Comparator
                    .comparingInt((Tag tag) -> {
                        int idx = priority.indexOf(tag.getName());
                        return idx >= 0 ? idx : priority.size();
                    })
                    .thenComparing(Tag::getName)
            );
        };
    }
}
