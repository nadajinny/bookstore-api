package com.example.bookstore_api.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI bookstoreOpenAPI() {
        return new OpenAPI()
            .info(
                new Info()
                    .title("BookStore-API")
                    .description("Spring Boot 기반 도서 관리 백엔드 API")
                    .version("1.0.0")
            );
    }
}
