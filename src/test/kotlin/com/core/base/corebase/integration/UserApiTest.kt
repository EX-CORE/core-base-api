package com.core.base.corebase.integration

import com.core.base.corebase.base.IntegrationTestSpec
import com.core.base.corebase.base.OrganizationDataSetup
import com.core.base.corebase.base.UserDataSetup
import com.core.base.corebase.controller.user.dto.UserOrganizationRes
import com.core.base.corebase.domain.user.code.MemberState
import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.matchers.shouldBe
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class UserApiTest(
    val mockMvc: MockMvc,
    val userDataSetup: UserDataSetup,
    val objectMapper: ObjectMapper,
    val organizationDataSetup: OrganizationDataSetup
): IntegrationTestSpec({

    Given("User have organizations and invited organizations"){
        val user = userDataSetup.addUser()
        val participationOrganizationId = organizationDataSetup.addMember(user = user, state = MemberState.JOIN).organization
        val invitedOrganizationId = organizationDataSetup.addMember(user = user, state = MemberState.WAIT).organization

        When("Request get user organization api"){
            val res = mockMvc.perform(
                get("/users/organizations")
                    .header("Authorization", "Bearer ${userDataSetup.getAccessToken(user)}")
            ).andDo(MockMvcResultHandlers.print())

            Then("Return 200") {
                res.andExpect(status().isOk)
            }

            Then("Return user organizations") {
                val result =
                    objectMapper.readValue(res.andReturn().response.contentAsString, UserOrganizationRes::class.java)
                result.participationOrganizations.size shouldBe 1
                result.invitedOrganizations.size shouldBe 1
                result.participationOrganizations[0].id shouldBe participationOrganizationId
                result.invitedOrganizations[0].id shouldBe invitedOrganizationId
            }
        }
    }

})