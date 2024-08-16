package com.core.base.corebase.support

import com.core.base.corebase.common.code.ErrorCode
import com.core.base.corebase.common.exception.BaseException
import com.core.base.corebase.config.AuthenticationFacade
import com.core.base.corebase.repository.AccountRepository
import com.core.base.corebase.repository.UserRepository
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpMethod
import org.springframework.web.servlet.HandlerInterceptor

class JwtInterceptor(
    private val authenticationFacade: AuthenticationFacade,
    private val jwtProvider: JwtProvider,
    private val userRepository: UserRepository
) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (!HttpMethod.OPTIONS.matches(request.method))
            request.getHeader("Authorization")
                ?.takeIf { it.startsWith("Bearer ") }
                ?.substring(7)
                ?.let { jwtProvider.getBody(it) }
                ?.takeIf { jwtProvider.isAccess(it) }
                ?.let { jwtProvider.getId(it) }
                ?.let { userRepository.findByUid(it) }
                ?.apply { authenticationFacade.setInfo(uid, email, name) }
                ?: throw BaseException(ErrorCode.INVALID_TOKEN)
        return super.preHandle(request, response, handler)
    }

}