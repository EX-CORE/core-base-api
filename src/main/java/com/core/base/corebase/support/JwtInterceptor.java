package com.core.base.corebase.support;

import com.core.base.corebase.common.code.ErrorCode;
import com.core.base.corebase.common.exception.BaseException;
import com.core.base.corebase.config.AuthenticationFacade;
import com.core.base.corebase.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

public class JwtInterceptor implements HandlerInterceptor {

    private final AuthenticationFacade authenticationFacade;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    public JwtInterceptor(AuthenticationFacade authenticationFacade,
                         JwtProvider jwtProvider,
                         UserRepository userRepository) {
        this.authenticationFacade = authenticationFacade;
        this.jwtProvider = jwtProvider;
        this.userRepository = userRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                            HttpServletResponse response,
                            Object handler) throws Exception {

        if (!HttpMethod.OPTIONS.matches(request.getMethod())) {
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                Claims claims = jwtProvider.getBody(token);

                if (jwtProvider.isAccess(claims)) {
                    userRepository.findByUid(jwtProvider.getId(claims))
                        .ifPresent(user -> authenticationFacade.setInfo(
                            user.getUid(),
                            user.getEmail(),
                            user.getName()
                        ));
                    return true;
                }
            }
            throw new BaseException(ErrorCode.INVALID_TOKEN);
        }
        return true;
    }
}
