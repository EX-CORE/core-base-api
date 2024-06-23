package com.core.base.corebase.integration

import com.core.base.corebase.common.code.LoginType
import com.core.base.corebase.config.GoogleProperties
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@SpringBootTest
@AutoConfigureMockMvc
class AuthApiTest(
    val mockMvc: MockMvc,
    @MockkBean val googleProperties: GoogleProperties,
) : BehaviorSpec({

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

})