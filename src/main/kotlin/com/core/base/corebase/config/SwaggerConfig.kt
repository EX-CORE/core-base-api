package com.core.base.corebase.config

import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc


@Configuration
@EnableWebMvc
class SwaggerConfig {

        @Bean
        fun openAPI(): OpenAPI {
            return OpenAPI()
                .components(Components())
                .info(apiInfo())
        }

        private fun apiInfo(): Info {
            return Info()
                .title("Core-Base")
                .description("Core-Base API - Swagger")
                .version("0.0.1-SNAPSHOT")
        }

}