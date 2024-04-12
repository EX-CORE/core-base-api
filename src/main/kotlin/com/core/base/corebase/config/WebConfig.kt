package com.core.base.corebase.config

import com.core.base.corebase.repository.AccountRepository
import com.core.base.corebase.repository.UserRepository
import com.core.base.corebase.support.JwtProvider
import net.huray.backend.minuting.support.JwtInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    private val authenticationFacade: AuthenticationFacade,
    private val accountRepository: AccountRepository,
    private val userRepository: UserRepository,
    private val jwtProvider: JwtProvider
) : WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("*")
            .allowedHeaders("*")
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(
            JwtInterceptor(authenticationFacade, accountRepository, userRepository, jwtProvider)
        ).addPathPatterns("/**")
            .excludePathPatterns(
                "/auth/**", "/error", "/swagger-resources/**", "/swagger-ui/**", "/v3/api-docs/**"
            )
    }
}