package com.core.base.corebase.service

import com.core.base.corebase.client.GoogleAuthClient
import com.core.base.corebase.client.GoogleInfoClient
import com.core.base.corebase.common.code.LoginType
import com.core.base.corebase.config.GoogleProperties
import com.core.base.corebase.service.auth.AuthService
import com.core.base.corebase.support.JwtProvider
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class AuthServiceTest: BehaviorSpec({

    val googleAuthClient: GoogleAuthClient = mockk()
    val googleInfoClient: GoogleInfoClient = mockk()
    val jwtProvider: JwtProvider = mockk()
    val googleProperties: GoogleProperties = mockk()

    val sut = AuthService(
        googleAuthClient,
        googleInfoClient,
        jwtProvider,
        googleProperties
    )

    Given("Google oauth info setting complete") {
        val clientId = "fake-client-id"
        val clientSecret = "fake-client-secret"
        val redirectUrl = "http://www.fake.com"
        val loginType = LoginType.REVIEWER

        every { googleProperties.clientId } returns clientId
        every { googleProperties.clientSecret } returns clientSecret
        every { googleProperties.redirectUrl } returns redirectUrl

        When("Request getUserGoogleCode()") {
            val userGoogleCodeRedirectUrl = sut.getUserGoogleCodeRedirectUrl(loginType)

            Then("Return correct url") {
                userGoogleCodeRedirectUrl.shouldBe(
                        "https://accounts.google.com/o/oauth2/v2/auth" +
                                "?client_id=${clientId}" +
                                "&scope=https://www.googleapis.com/auth/userinfo.email%20https://www.googleapis.com/auth/userinfo.profile" +
                                "&response_type=code&access_type=offline" +
                                "&state=state_parameter_passthrough_value&include_granted_scopes=true" +
                                "&redirect_uri=${redirectUrl}/${loginType.url}" +
                                "&prompt=consent"
                )
            }
        }
    }

})