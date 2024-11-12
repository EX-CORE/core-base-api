package com.core.base.corebase.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc


@Configuration
@EnableWebMvc
class SwaggerConfig {

    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI()
            .components(Components().addSecuritySchemes("bearer-jwt",
                SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")))
            .info(apiInfo())
            .addSecurityItem(SecurityRequirement().addList("bearer-jwt")) // 보안 요구사항 추가
    }

    private fun apiInfo(): Info {
        return Info()
            .title("Core-Base")
            .description("Core-Base API - Swagger")
            .version("0.0.1-SNAPSHOT")
    }

}