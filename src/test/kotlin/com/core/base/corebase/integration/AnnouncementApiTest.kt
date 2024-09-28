package com.core.base.corebase.integration

import com.core.base.corebase.base.IntegrationTestSpec
import com.core.base.corebase.controller.organization.dto.AnnouncementReq
import com.core.base.corebase.controller.organization.dto.AnnouncementRes
import com.core.base.corebase.domain.organization.Announcement
import com.core.base.corebase.domain.organization.Organization
import com.core.base.corebase.domain.user.Account
import com.core.base.corebase.domain.user.Member
import com.core.base.corebase.domain.user.User
import com.core.base.corebase.domain.user.code.MemberState
import com.core.base.corebase.domain.user.code.PermissionType
import com.core.base.corebase.domain.user.code.UserState
import com.core.base.corebase.repository.*
import com.core.base.corebase.support.JwtProvider
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.matchers.shouldBe
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDate
import java.time.LocalDateTime

class AnnouncementApiTest(
    val mockMvc: MockMvc,
    val userRepository: UserRepository,
    val accountRepository: AccountRepository,
    val organizationRepository: OrganizationRepository,
    val memberRepository: MemberRepository,
    val announcementRepository: AnnouncementRepository,
    val jwtProvider: JwtProvider,
    val objectMapper: ObjectMapper,
) : IntegrationTestSpec({

    val user: User = userRepository.save(User("test", "test"))
    val account: Account = accountRepository.save(Account("refreshToken", UserState.ACTIVE, user.uid))

    val managerOrganization: Organization = organizationRepository.save(Organization("managerOrganization", "test", "test", "test", "test", mutableListOf()))
    val managerMember = memberRepository.save(Member(user.email, user.name, user.uid, managerOrganization.id, null, MemberState.JOIN, PermissionType.MANAGER))

    val reviewerOrganization: Organization = organizationRepository.save(Organization("reviewerOrganization", "test", "test", "test", "test", mutableListOf()))
    val reviewerMember = memberRepository.save(Member(user.email, user.name, user.uid, reviewerOrganization.id, null, MemberState.JOIN, PermissionType.REVIEWER))

    val accessToken = jwtProvider.generateAccessToken(user.uid)

    fun getBaseMockMvc(builder: MockHttpServletRequestBuilder) =
        mockMvc.perform(builder.header("Authorization", "Bearer $accessToken"))
            .andDo(MockMvcResultHandlers.print())

    Given("Exists two announcement") {
        val announcementList = announcementRepository.saveAll(listOf(
            Announcement(reviewerMember.organizationId, "test1", "test1", LocalDateTime.now().minusDays(1)),
            Announcement(reviewerMember.organizationId, "test2", "test2", LocalDateTime.now().minusDays(2)),
            Announcement(managerMember.organizationId, "test1", "test1", LocalDateTime.now().minusDays(1)),
            Announcement(managerMember.organizationId, "test2", "test2", LocalDateTime.now().minusDays(2))
        ))

        When("Request get announcement list api by reviewer organization") {
            val res = getBaseMockMvc(get("/announcement")
                .queryParam("organizationId", reviewerOrganization.id.toString())
            )

            Then("Return 200") {
                res.andExpect(status().isOk)
            }

            Then("Return correct announcement list") {
                val result = objectMapper.readValue(res.andReturn().response.contentAsString, object : TypeReference<List<AnnouncementRes>>() {})
                result.size shouldBe 2
                result[0].id shouldBe announcementList[0].id
                result[1].id shouldBe announcementList[1].id
            }
        }
    }

    Given("Exists manager organization") {
        val announcementReq = AnnouncementReq("title", "content")

        When("Request create announcement api by manager organization") {
            val res = getBaseMockMvc(post("/announcement")
                .queryParam("organizationId", managerOrganization.id.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(announcementReq))
            )

            Then("Return 201") {
                res.andExpect(status().isCreated)
            }

            Then("Created announcement") {
                val announcementList = announcementRepository.findAll()
                announcementList.size shouldBe 1
                announcementList[0].title shouldBe announcementReq.title
                announcementList[0].content shouldBe announcementReq.content
            }
        }
    }

    Given("Exists reviewer organization") {
        val announcementReq = AnnouncementReq("title", "content")

        When("Request create announcement api by reviewer organization") {
            val res = getBaseMockMvc(post("/announcement")
                .queryParam("organizationId", reviewerOrganization.id.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(announcementReq))
            )

            Then("Return 401") {
                res.andExpect(status().isForbidden())
            }
        }
    }

    Given("Exists announcement in manager organization") {
        val announcement = announcementRepository.save(Announcement(managerOrganization.id, "title", "content"))

        When("Request update announcement api") {
            val announcementReq = AnnouncementReq("updateTitle", "updateContent")

            val res = getBaseMockMvc(put("/announcement/{announcementId}", announcement.id.toString())
                .queryParam("organizationId", managerOrganization.id.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(announcementReq))
            )

            Then("Return 200") {
                res.andExpect(status().isOk)
            }

            Then("Updated announcement") {
                val announcementList = announcementRepository.findAll()
                announcementList.size shouldBe 1
                announcementList[0].title shouldBe announcementReq.title
                announcementList[0].content shouldBe announcementReq.content
            }
        }

        When("Request delete announcement api") {
            val res = getBaseMockMvc(delete("/announcement/{announcementId}", announcement.id.toString())
                .queryParam("organizationId", managerOrganization.id.toString()))

            Then("Return 204") {
                res.andExpect(status().isNoContent)
            }
        }
    }

})