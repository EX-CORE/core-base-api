package com.core.base.corebase.config;

import com.core.base.corebase.repository.UserRepository;
import com.core.base.corebase.support.JwtInterceptor;
import com.core.base.corebase.support.JwtProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuthenticationFacade authenticationFacade;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    public WebConfig(AuthenticationFacade authenticationFacade,
                     JwtProvider jwtProvider,
                     UserRepository userRepository) {
        this.authenticationFacade = authenticationFacade;
        this.jwtProvider = jwtProvider;
        this.userRepository = userRepository;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtInterceptor(authenticationFacade, jwtProvider, userRepository))
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/health-check", "/auth/**", "/error", "/swagger-resources/**", "/swagger-ui/**", "/v3/api-docs/**"
                );
    }
}
