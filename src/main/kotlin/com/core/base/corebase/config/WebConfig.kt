package com.core.base.corebase.config

import com.core.base.corebase.repository.AccountRepository
import com.core.base.corebase.repository.UserRepository
import com.core.base.corebase.support.JwtInterceptor
import com.core.base.corebase.support.JwtProvider
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    private val authenticationFacade: AuthenticationFacade,
    private val jwtProvider: JwtProvider,
    private val accountRepository: AccountRepository,
    private val userRepository: UserRepository
) : WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("*")
            .allowedHeaders("*")
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(
            JwtInterceptor(authenticationFacade, jwtProvider, accountRepository, userRepository)
        ).addPathPatterns("/**")
            .excludePathPatterns(
                "/health-check", "/auth/**", "/error", "/swagger-resources/**", "/swagger-ui/**", "/v3/api-docs/**"
            )
    }
}