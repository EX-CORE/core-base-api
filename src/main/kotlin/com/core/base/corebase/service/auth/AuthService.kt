package com.core.base.corebase.service.auth

import com.core.base.corebase.client.GoogleAuthClient
import com.core.base.corebase.client.GoogleInfoClient
import com.core.base.corebase.client.dto.AuthDto
import com.core.base.corebase.client.dto.GoogleDto
import com.core.base.corebase.common.code.LoginType
import com.core.base.corebase.config.GoogleProperties
import com.core.base.corebase.support.JwtProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val googleAuthClient: GoogleAuthClient,
    private val googleInfoClient: GoogleInfoClient,
    private val jwtProvider: JwtProvider,
    private val googleProperties: GoogleProperties
) {

    fun getUserGoogleCodeRedirectUrl(type: LoginType): String = with(googleProperties) {
        "https://accounts.google.com/o/oauth2/v2/auth" +
                "?client_id=${clientId}" +
                "&scope=https://www.googleapis.com/auth/userinfo.email%20https://www.googleapis.com/auth/userinfo.profile" +
                "&response_type=code&access_type=offline" +
                "&state=state_parameter_passthrough_value&include_granted_scopes=true" +
                "&redirect_uri=${redirectUrl}/${type.url}" +
                "&prompt=consent"
    }


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
        // TODO
        throw UnsupportedOperationException()
    }

    @Transactional(readOnly = true)
    fun tokenRefresh(req: AuthDto.TokenRefreshReq): AuthDto.TokenRefreshRes = throw UnsupportedOperationException() // TODO

}