package com.core.base.corebase.service

import com.core.base.corebase.common.code.ErrorCode
import com.core.base.corebase.common.exception.BaseException
import com.core.base.corebase.controller.organization.dto.AnnouncementReq
import com.core.base.corebase.domain.organization.Announcement
import com.core.base.corebase.domain.user.Member
import com.core.base.corebase.domain.user.code.MemberState
import com.core.base.corebase.domain.user.code.PermissionType
import com.core.base.corebase.repository.AnnouncementRepository
import com.core.base.corebase.repository.MemberRepository
import com.core.base.corebase.service.organization.AnnouncementService
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.util.*

class AnnouncementServiceTest: BehaviorSpec({

    val memberRepository: MemberRepository = mockk()
    val announcementRepository: AnnouncementRepository = mockk()

    val sut = AnnouncementService(
        memberRepository,
        announcementRepository
    )

    Given("User is not organization member") {
        val uid = UUID.randomUUID()
        val organizationId = UUID.randomUUID()
        every { memberRepository.findByUidAndOrganizationIdAndState(uid, organizationId, MemberState.JOIN) } returns null

        When("Request getList(uid, organization)") {
            val exception = shouldThrow<BaseException> {
                sut.getList(uid, organizationId)
            }

            Then("Throw InvalidTokenException") {
                exception.errorCode shouldBe ErrorCode.INVALID_TOKEN
            }
        }

        When("Request save(announcementReq, uid, organization)") {
            val exception = shouldThrow<BaseException> {
                sut.save(AnnouncementReq("title", "content"), uid, organizationId)
            }

            Then("Throw InvalidTokenException") {
                exception.errorCode shouldBe ErrorCode.INVALID_TOKEN
            }
        }

        When("Request update(announcementReq, uid, organization, announcementId)") {
            val exception = shouldThrow<BaseException> {
                sut.update(AnnouncementReq("title", "content"), uid, organizationId, UUID.randomUUID())
            }

            Then("Throw InvalidTokenException") {
                exception.errorCode shouldBe ErrorCode.INVALID_TOKEN
            }
        }

        When("Request delete(uid, organization, announcementId)") {
            val exception = shouldThrow<BaseException> {
                sut.delete(uid, organizationId, UUID.randomUUID())
            }

            Then("Throw InvalidTokenException") {
                exception.errorCode shouldBe ErrorCode.INVALID_TOKEN
            }
        }
    }

    Given("User is not manager member") {
        val uid = UUID.randomUUID()
        val organizationId = UUID.randomUUID()
        every { memberRepository.findByUidAndOrganizationIdAndState(uid, organizationId, MemberState.JOIN) } returns
            Member(
                "email@email.com",
                "가나다",
                uid,
                organizationId,
                null,
                MemberState.JOIN,
                PermissionType.REVIEWER
            )

        When("Request save(announcementReq, uid, organization)") {
            val exception = shouldThrow<BaseException> {
                sut.save(AnnouncementReq("title", "content"), uid, organizationId)
            }

            Then("Throw InvalidTokenException") {
                exception.errorCode shouldBe ErrorCode.INVALID_TOKEN
            }
        }

        When("Request update(announcementReq, uid, organization, announcementId)") {
            val exception = shouldThrow<BaseException> {
                sut.update(AnnouncementReq("title", "content"), uid, organizationId, UUID.randomUUID())
            }

            Then("Throw InvalidTokenException") {
                exception.errorCode shouldBe ErrorCode.INVALID_TOKEN
            }
        }

        When("Request delete(uid, organization, announcementId)") {
            val exception = shouldThrow<BaseException> {
                sut.delete(uid, organizationId, UUID.randomUUID())
            }

            Then("Throw InvalidTokenException") {
                exception.errorCode shouldBe ErrorCode.INVALID_TOKEN
            }
        }
    }

    Given("Announcement exists") {
        val uid = UUID.randomUUID()
        val organizationId = UUID.randomUUID()
        every { memberRepository.findByUidAndOrganizationIdAndState(uid, organizationId, MemberState.JOIN) } returns
                Member(
                    "email@email.com",
                    "가나다",
                    uid,
                    organizationId,
                    null,
                    MemberState.JOIN,
                    PermissionType.MANAGER
                )
        every { announcementRepository.findByOrganizationIdOrderByCreatedAtDesc(organizationId) } returns
                listOf(
                    Announcement(
                        organizationId,
                        "title",
                        "content"
                    )
                )

        When("Request getList(uid, organization)") {
            val res = sut.getList(uid, organizationId)

            Then("Return announcement list") {
                res.size shouldBe 1
            }
        }
    }

})