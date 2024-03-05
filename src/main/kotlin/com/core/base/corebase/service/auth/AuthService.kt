package com.core.base.corebase.service.auth

import com.core.base.corebase.client.GoogleAuthClient
import com.core.base.corebase.client.GoogleInfoClient
import com.core.base.corebase.client.dto.AuthDto
import com.core.base.corebase.client.dto.GoogleDto
import com.core.base.corebase.common.exception.BaseException
import com.core.base.corebase.common.exception.code.ErrorCode
import com.core.base.corebase.common.exception.code.ServerType
import com.core.base.corebase.domain.user.Account
import com.core.base.corebase.repository.AccountRepository
import com.core.base.corebase.repository.UserRepository
import com.core.base.corebase.config.GoogleProperties
import com.core.base.corebase.support.JwtProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.net.URLEncoder

@Service
class AuthService(
    private val googleAuthClient: GoogleAuthClient,
    private val googleInfoClient: GoogleInfoClient,
    private val jwtProvider: JwtProvider,
    private val googleProperties: GoogleProperties,
    private val accountRepository: AccountRepository,
    private val userRepository: UserRepository
) {


    fun getUserGoogleCode(type: ServerType): String = with(googleProperties) {
        "https://accounts.google.com/o/oauth2/v2/auth" +
                "?client_id=${clientId}" +
                "&scope=https://www.googleapis.com/auth/userinfo.email%20https://www.googleapis.com/auth/userinfo.profile" +
                "&response_type=code&access_type=offline" +
                "&state=state_parameter_passthrough_value&include_granted_scopes=true" +
                "&redirect_uri=${URLEncoder.encode(if (type == ServerType.PROD) redirectUrl else "http://localhost:5000/login", "UTF-8")}" +
                "&prompt=consent"
    }


    @Transactional
    fun login(code: String, type: ServerType): AuthDto.LoginRes = with(googleProperties) {
        val authResponse = GoogleDto.GoogleTokenReq(
            code,
            clientId,
            clientSecret,
            if (type == ServerType.PROD) redirectUrl else "http://localhost:5000/login",
            "authorization_code"
        ).let {
            googleAuthClient.getTokenByCode(it)
        }
        authResponse.run { googleInfoClient.getInfo("Bearer $accessToken") }
            .let { googleInfoRes ->
                val user = userRepository.findByEmail(googleInfoRes.email)
                    .orElseThrow { throw BaseException(ErrorCode.USER_NOT_FOUND) }
                accountRepository.save(Account(user.uid, authResponse.refreshToken));
            }
            .run { AuthDto.LoginRes(jwtProvider.generateAccessToken(uid), jwtProvider.generateRefreshToken(uid)) }
    }

    fun tokenRefresh(req: AuthDto.TokenRefreshReq): AuthDto.TokenRefreshRes = with(jwtProvider) {
        req.run { getBody(refreshToken) }
            .takeIf { isRefresh(it) }
            ?.let { getId(it) }
            ?.takeIf { accountRepository.existsById(it) }
            ?.let { AuthDto.TokenRefreshRes(jwtProvider.generateAccessToken(it)) }
            ?: throw BaseException(ErrorCode.INVALID_TOKEN)
    }

}