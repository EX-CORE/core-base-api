package com.core.base.corebase.service.auth

import com.core.base.corebase.client.GoogleAuthClient
import com.core.base.corebase.client.GoogleInfoClient
import com.core.base.corebase.client.dto.AuthDto
import com.core.base.corebase.client.dto.GoogleDto
import com.core.base.corebase.common.code.ErrorCode
import com.core.base.corebase.common.exception.BaseException
import com.core.base.corebase.config.GoogleProperties
import com.core.base.corebase.domain.user.Account
import com.core.base.corebase.domain.user.User
import com.core.base.corebase.domain.user.code.UserState
import com.core.base.corebase.repository.AccountRepository
import com.core.base.corebase.repository.MemberRepository
import com.core.base.corebase.repository.UserRepository
import com.core.base.corebase.support.JwtProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val googleAuthClient: GoogleAuthClient,
    private val googleInfoClient: GoogleInfoClient,
    private val jwtProvider: JwtProvider,
    private val googleProperties: GoogleProperties,
    private val accountRepository: AccountRepository,
    private val userRepository: UserRepository,
    private val memberRepository: MemberRepository
) {


    fun getUserGoogleCode(): String = with(googleProperties) {
        "https://accounts.google.com/o/oauth2/v2/auth" +
                "?client_id=${clientId}" +
                "&scope=https://www.googleapis.com/auth/userinfo.email%20https://www.googleapis.com/auth/userinfo.profile" +
                "&response_type=code&access_type=offline" +
                "&state=state_parameter_passthrough_value&include_granted_scopes=true" +
                "&redirect_uri=${redirectUrl}" +
                "&prompt=consent"
    }

    @Transactional
    fun login(code: String): AuthDto.LoginRes = with(googleProperties) {
        GoogleDto.GoogleTokenReq(
            code,
            clientId,
            clientSecret,
            "${redirectUrl}",
            "authorization_code"
        ).let {
            val accessTokenResponse = googleAuthClient.getTokenByCode(it)
            val googleInfoResponse = googleInfoClient.getInfo("Bearer ${accessTokenResponse.accessToken}")
            val user = userRepository.findByEmail(googleInfoResponse.email)
                ?: userRepository.save(User(googleInfoResponse.name, googleInfoResponse.email, googleInfoResponse.picture))
                    .also { accountRepository.save(Account(accessTokenResponse.refreshToken, UserState.ACTIVE, it)) }

            return AuthDto.LoginRes(
                jwtProvider.generateAccessToken(user.uid),
                jwtProvider.generateRefreshToken(user.uid)
            )
        }
    }

    @Transactional(readOnly = true)
    fun tokenRefresh(req: AuthDto.TokenRefreshReq): AuthDto.TokenRefreshRes = with(jwtProvider) {
        req.run { getBody(refreshToken) }
            .takeIf { isRefresh(it) }
            ?.let { getId(it) }
            ?.takeIf { accountRepository.existsByUid(it) }
            ?.let { AuthDto.TokenRefreshRes(jwtProvider.generateAccessToken(it)) }
            ?: throw BaseException(ErrorCode.INVALID_TOKEN)
    }

}