package com.core.base.corebase.service.auth

import com.core.base.corebase.client.GoogleAuthClient
import com.core.base.corebase.client.GoogleInfoClient
import com.core.base.corebase.client.dto.AuthDto
import com.core.base.corebase.client.dto.GoogleDto
import com.core.base.corebase.common.code.ErrorCode
import com.core.base.corebase.common.code.LoginType
import com.core.base.corebase.common.exception.BaseException
import com.core.base.corebase.config.GoogleProperties
import com.core.base.corebase.controller.user.dto.UserReq
import com.core.base.corebase.domain.user.Account
import com.core.base.corebase.domain.user.User
import com.core.base.corebase.domain.user.code.PermissionType
import com.core.base.corebase.domain.user.code.UserState
import com.core.base.corebase.repository.AccountRepository
import com.core.base.corebase.repository.UserRepository
import com.core.base.corebase.support.JwtProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class AuthService(
    private val googleAuthClient: GoogleAuthClient,
    private val googleInfoClient: GoogleInfoClient,
    private val jwtProvider: JwtProvider,
    private val googleProperties: GoogleProperties,
    private val accountRepository: AccountRepository,
    private val userRepository: UserRepository
) {


    fun getUserGoogleCode(type: LoginType): String = with(googleProperties) {
        "https://accounts.google.com/o/oauth2/v2/auth" +
                "?client_id=${clientId}" +
                "&scope=https://www.googleapis.com/auth/userinfo.email%20https://www.googleapis.com/auth/userinfo.profile" +
                "&response_type=code&access_type=offline" +
                "&state=state_parameter_passthrough_value&include_granted_scopes=true" +
                "&redirect_uri=${redirectUrl}/${type.url}" +
                "&prompt=consent"
    }


    /*
    * 1. 구글 토큰 확인
    * 2. 기존 account 있는지 확인
    * 2.1. 없는 경우, member에 매핑된게 있는지 확인
    * 2.1.1. 매핑된게 있으면 그 정보로 회원가입 진행
    * 2.1.2. 매핑된게 없으면 회사만들지, 관리자 문의 alert
    * 2.2. 있는 경우 기존 계정 정보로 로그인처리
    *
    * */

    @Transactional
    fun login(code: String, type: LoginType): AuthDto.LoginRes = with(googleProperties) {
        val authResponse = GoogleDto.GoogleTokenReq(
            code,
            clientId,
            clientSecret,
            "${redirectUrl}/${type.url}",
            "authorization_code"
        ).let { googleAuthClient.getTokenByCode(it) }
        val infoResponse = authResponse.run { googleInfoClient.getInfo("Bearer $accessToken") }
        userRepository.findByEmail(infoResponse.email)
            ?.let {
                AuthDto.LoginRes(
                    jwtProvider.generateAccessToken(it.uid),
                    jwtProvider.generateRefreshToken(it.uid)
                )
            } ?: throw BaseException(ErrorCode.USER_NOT_FOUND)

// TODO:: 유저 회사별 대기 여부 관련 API , 기획 필요
//        when (type) {
//            LoginType.REVIEWER ->
//                infoResponse
//                    .run { userRepository.findByEmail(email) ?: throw BaseException(ErrorCode.USER_NOT_FOUND) }
//                    .apply { if (isWait()) updateActive() }
//            LoginType.MANAGER ->
//                infoResponse
//                    .run {
//                        userRepository.findByEmail(email)
//                            ?: userRepository.save(User(
//                                UUID.randomUUID(), email, name, null, UserState.ACTIVE, null, PermissionType.MANAGER
//                            ))
//                    }
//        }.run { accountRepository.save(Account(uid, authResponse.refreshToken)) }
//            .let {
//                AuthDto.LoginRes(
//                    jwtProvider.generateAccessToken(uid),
//                    jwtProvider.generateRefreshToken(uid)
//                )
//            }
    }

    @Transactional(readOnly = true)
    fun tokenRefresh(req: AuthDto.TokenRefreshReq): AuthDto.TokenRefreshRes = with(jwtProvider) {
        req.run { getBody(refreshToken) }
            .takeIf { isRefresh(it) }
            ?.let { getId(it) }
            ?.takeIf { accountRepository.existsById(it) }
            ?.let { AuthDto.TokenRefreshRes(jwtProvider.generateAccessToken(it)) }
            ?: throw BaseException(ErrorCode.INVALID_TOKEN)
    }

}