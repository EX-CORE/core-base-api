package com.core.base.corebase.service

import com.core.base.corebase.client.GoogleAuthClient
import com.core.base.corebase.client.GoogleInfoClient
import com.core.base.corebase.client.dto.GoogleDto
import com.core.base.corebase.config.GoogleProperties
import com.core.base.corebase.domain.user.Account
import com.core.base.corebase.domain.user.User
import com.core.base.corebase.domain.user.code.UserState
import com.core.base.corebase.repository.AccountRepository
import com.core.base.corebase.repository.MemberRepository
import com.core.base.corebase.repository.UserRepository
import com.core.base.corebase.service.auth.AuthService
import com.core.base.corebase.support.JwtProvider
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.util.*

class AuthServiceTest: BehaviorSpec({

    val googleAuthClient: GoogleAuthClient = mockk()
    val googleInfoClient: GoogleInfoClient = mockk()
    val jwtProvider: JwtProvider = mockk()
    val googleProperties: GoogleProperties = mockk()
    val accountRepository: AccountRepository = mockk()
    val userRepository: UserRepository = mockk()
    val memberRepository: MemberRepository = mockk()

    val sut = AuthService(
        googleAuthClient,
        googleInfoClient,
        jwtProvider,
        googleProperties,
        accountRepository,
        userRepository,
        memberRepository
    )

    Given("Google oauth info setting complete") {
        val clientId = "fake-client-id"
        val clientSecret = "fake-client-secret"
        val redirectUrl = "http://www.fake.com"

        every { googleProperties.clientId } returns clientId
        every { googleProperties.clientSecret } returns clientSecret
        every { googleProperties.redirectUrl } returns redirectUrl

        When("Request getUserGoogleCode()") {
            val userGoogleCodeRedirectUrl = sut.getUserGoogleCode()

            Then("Return correct url") {
                userGoogleCodeRedirectUrl.shouldBe(
                        "https://accounts.google.com/o/oauth2/v2/auth" +
                                "?client_id=${clientId}" +
                                "&scope=https://www.googleapis.com/auth/userinfo.email%20https://www.googleapis.com/auth/userinfo.profile" +
                                "&response_type=code&access_type=offline" +
                                "&state=state_parameter_passthrough_value&include_granted_scopes=true" +
                                "&redirect_uri=${redirectUrl}" +
                                "&prompt=consent"
                )
            }
        }

    }


    Given("User Login Success") {
        val clientId = "fake-client-id"
        val clientSecret = "fake-client-secret"
        val redirectUrl = "http://www.fake.com"
        val code = "code"
        val uid = UUID.randomUUID()

        val user = User(uid, "name", "email")

        every { googleProperties.clientId } returns clientId
        every { googleProperties.clientSecret } returns clientSecret
        every { googleProperties.redirectUrl } returns redirectUrl

        every { googleAuthClient.getTokenByCode(any()) } returns GoogleDto.GoogleTokenRes("accessToken", "expiresIn","tokenType", "scope", "refreshToken")
        every { googleInfoClient.getInfo(any()) } returns GoogleDto.GoogleInfoRes("sub", "name", "given", "family", "picture", "test@test.com",
            "verified", "korean")

        every { jwtProvider.generateAccessToken(uid) } returns "accessToken"
        every { jwtProvider.generateRefreshToken(uid) } returns "refreshToken"

        And("User exists") {
            every { userRepository.findByEmail(eq("test@test.com")) } returns user

            When("Request login(code, loginType) - sign in") {
                val loginResult = sut.login(code)

                Then("Return Sign in") {
                    loginResult.accessToken.shouldBe("accessToken")
                    loginResult.refreshToken.shouldBe("refreshToken")
                }
            }
        }

        And("User exists") {
            every { userRepository.findByEmail(eq("test@test.com")) } returns null
            every { userRepository.save(any()) } returns user
            every { accountRepository.save(any()) } returns Account(uid, "refreshToken", UserState.ACTIVE)

            When("Request login(code, loginType) - sign up") {
                val loginResult = sut.login(code)

                Then("Return Sign up") {
                    loginResult.accessToken.shouldBe("accessToken")
                    loginResult.refreshToken.shouldBe("refreshToken")
                }
            }
        }
    }

})