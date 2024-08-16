package com.core.base.corebase.integration

import com.core.base.corebase.base.IntegrationTestSpec
import com.core.base.corebase.base.UserDataSetup
import com.core.base.corebase.client.GoogleAuthClient
import com.core.base.corebase.client.GoogleInfoClient
import com.core.base.corebase.client.dto.AuthDto
import com.core.base.corebase.client.dto.GoogleDto
import com.core.base.corebase.common.code.LoginType
import com.core.base.corebase.config.GoogleProperties
import com.core.base.corebase.repository.AccountRepository
import com.core.base.corebase.repository.UserRepository
import com.core.base.corebase.support.JwtProvider
import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.kotest.matchers.shouldBe
import io.mockk.every
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class AuthApiTest(
    val mockMvc: MockMvc,
    val userRepository: UserRepository,
    val accountRepository: AccountRepository,
    val jwtProvider: JwtProvider,
    val objectMapper: ObjectMapper,
    @MockkBean val googleProperties: GoogleProperties,
    @MockkBean val googleAuthClient: GoogleAuthClient,
    @MockkBean val googleInfoClient: GoogleInfoClient,
    val userDataSetup: UserDataSetup
) : IntegrationTestSpec({

    Given("Google oauth info setting complete") {
        val clientId = "fake-client-id"
        val clientSecret = "fake-client-secret"
        val redirectUrl = "http://www.fake.com"
        val loginType = LoginType.REVIEWER

        every { googleProperties.clientId } returns clientId
        every { googleProperties.clientSecret } returns clientSecret
        every { googleProperties.redirectUrl } returns redirectUrl

        When("Request move user google code api") {
            val res = mockMvc.perform(get("/auth/code")
                .param("type", loginType.name)).andDo(print())

            Then("Return 302 status") {
                res.andExpect(status().`is`(302))
            }

            Then("Redirect correct url") {
                res.andExpect(
                    redirectedUrl(
                        "https://accounts.google.com/o/oauth2/v2/auth" +
                                "?client_id=${clientId}" +
                                "&scope=https://www.googleapis.com/auth/userinfo.email%20https://www.googleapis.com/auth/userinfo.profile" +
                                "&response_type=code&access_type=offline" +
                                "&state=state_parameter_passthrough_value&include_granted_scopes=true" +
                                "&redirect_uri=${redirectUrl}/${loginType.url}" +
                                "&prompt=consent"
                    )
                )
            }
        }
    }

    Given("Google code is valid") {
        val authCode = "fake-code"
        val clientId = "fake-client-id"
        val clientSecret = "fake-client-secret"
        val redirectUrl = "http://www.fake.com"
        val loginType = LoginType.REVIEWER
        val googleAccessToken = "fake-google-access-token"
        val email = "email@email.com"

        every { googleProperties.clientId } returns clientId
        every { googleProperties.clientSecret } returns clientSecret
        every { googleProperties.redirectUrl } returns redirectUrl

        every { googleAuthClient.getTokenByCode(any()) } returns
                GoogleDto.GoogleTokenRes(
                    googleAccessToken, "expiresIn", "tokenType",
                    "scope", "refreshToken"
                )

        every { googleInfoClient.getInfo("Bearer ${googleAccessToken}") } returns
                GoogleDto.GoogleInfoRes(
                    "sub", "name", "givenName",
                    "familyName", "picture", email,
                    "emailVerified", "locale"
                )

        And("User not exists") {

            When("Request login api") {
                val res = mockMvc.perform(post("/auth/login")
                    .param("code", authCode)
                    .param("type", loginType.name)).andDo(print())

                Then("Return 200") {
                    res.andExpect(status().isOk)
                }

                Then("User created") {
                    val allUser = userRepository.findAll()
                    allUser.size shouldBe 1
                    allUser.get(0).email shouldBe email

                    val allAccount = accountRepository.findAll()
                    allAccount.size shouldBe 1
                    allAccount.get(0).uid shouldBe allUser.get(0).uid
                }
            }
        }

        And("User exists") {
            val user = userDataSetup.addUser()

            When("Request login api") {
                val res = mockMvc.perform(post("/auth/login")
                    .param("code", authCode)
                    .param("type", loginType.name)).andDo(print())

                Then("Return 200") {
                    res.andExpect(status().isOk)
                }

                Then("User not created") {
                    val allUser = userRepository.findAll()
                    allUser.size shouldBe 1
                    allUser.get(0).uid shouldBe user.uid

                    val allAccount = accountRepository.findAll()
                    allAccount.size shouldBe 1
                    allAccount.get(0).uid shouldBe user.uid
                }
            }
        }
    }

    Given("User already login") {
        val user = userDataSetup.addUser()
        val refreshToken = jwtProvider.generateRefreshToken(user.uid)

        When("Request token refresh api") {
            val res = mockMvc.perform(post("/auth/token-refresh")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(AuthDto.TokenRefreshReq(refreshToken)))
            ).andDo(print())

            Then("Return 200") {
                res.andExpect(status().isOk)
            }

            Then("AccessToken is valid") {
                val result =
                    objectMapper.readValue(res.andReturn().response.contentAsString, AuthDto.TokenRefreshRes::class.java)
                jwtProvider.getBody(result.accessToken)
                    .also {
                        jwtProvider.isAccess(it) shouldBe true
                        jwtProvider.getId(it) shouldBe user.uid
                    }

            }
        }
    }

})