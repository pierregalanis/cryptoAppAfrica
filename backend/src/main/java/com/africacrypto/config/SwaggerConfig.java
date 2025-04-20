package com.africacrypto.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class SwaggerConfig {
    @Bean
    @Primary
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
            .title("AfricaCrypto API")
            .version("1.0")
            .description("API documentation for AfricaCrypto backend"));
    }
}
