package com.core.base.corebase.service

import com.core.base.corebase.domain.organization.Organization
import com.core.base.corebase.domain.user.Member
import com.core.base.corebase.domain.user.code.MemberState
import com.core.base.corebase.domain.user.code.PermissionType
import com.core.base.corebase.repository.MemberRepository
import com.core.base.corebase.repository.OrganizationRepository
import com.core.base.corebase.service.user.UserService
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.util.*

class UserServiceTest: BehaviorSpec({

    val memberRepository = mockk<MemberRepository>()
    val organizationRepository = mockk<OrganizationRepository>()

    val sut = UserService(memberRepository, organizationRepository)

    Given("User's organization exists") {
        val uid = UUID.randomUUID()

        val organization1 = Organization("organization1",
            null, null, null, null, null)
        val member1 = Member("member1@email.com", "홍길동", uid,
            organization1.id, null, MemberState.JOIN, PermissionType.REVIEWER)

        val organization2 = Organization("organization1",
            null, null, null, null, null)
        val member2 = Member("member2@email.com", "고길동", uid,
            organization2.id, null, MemberState.WAIT, PermissionType.REVIEWER)


        every { memberRepository.findByUid(any()) } returns listOf(member1, member2)
        every { organizationRepository.findAllById(any()) } answers {
            val ids = arg<List<UUID>>(0)
            when {
                ids.contains(organization1.id) -> listOf(organization1)
                ids.contains(organization2.id) -> listOf(organization2)
                else -> emptyList()
            }
        }

        When("Request getUserOrganization(uid)") {
            val result = sut.getUserOrganization(uid)

            Then("Return organization info") {
                result.participationOrganizations.size shouldBe 1
                result.invitedOrganizations.size shouldBe 1
                result.participationOrganizations[0].id shouldBe organization1.id
                result.invitedOrganizations[0].id shouldBe organization2.id
            }
        }
    }


})