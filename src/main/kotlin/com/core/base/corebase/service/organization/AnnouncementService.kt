package com.core.base.corebase.service.organization

import com.core.base.corebase.common.code.ErrorCode
import com.core.base.corebase.common.exception.BaseException
import com.core.base.corebase.controller.organization.dto.AnnouncementReq
import com.core.base.corebase.controller.organization.dto.AnnouncementRes
import com.core.base.corebase.domain.organization.Announcement
import com.core.base.corebase.domain.user.code.MemberState
import com.core.base.corebase.domain.user.code.PermissionType
import com.core.base.corebase.repository.AnnouncementRepository
import com.core.base.corebase.repository.MemberRepository
import com.core.base.corebase.repository.OrganizationRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class AnnouncementService(
    private val memberRepository: MemberRepository,
    private val organizationRepository: OrganizationRepository,
    private val announcementRepository: AnnouncementRepository
) {

    @Transactional
    fun getList(uid: UUID, organizationId: Long): List<AnnouncementRes> {
        memberRepository.findByUser_UidAndOrganizationIdAndState(uid, organizationId, MemberState.JOIN) ?: throw BaseException(ErrorCode.ANNOUNCEMENT_NOT_ALLOWED)
        return organizationId
            .let { announcementRepository.findByOrganizationIdOrderByCreatedAtDesc(it) }
            .map { it.toRes() }
    }

    private fun Announcement.toRes(): AnnouncementRes = AnnouncementRes(id, title, content, createdAt)

    @Transactional
    fun save(announcementReq: AnnouncementReq, uid: UUID, organizationId: Long) {
        var organization = organizationRepository.findByIdOrNull(organizationId) ?: throw BaseException(ErrorCode.ORGANIZATION_NOT_FOUND, organizationId)
        checkAuthorization(uid, organizationId)
        announcementRepository.save(Announcement(organization, announcementReq.title, announcementReq.content));
    }

    @Transactional
    fun update(announcementReq: AnnouncementReq, uid: UUID, organizationId: Long, announcementId: Long) {
        checkAuthorization(uid, organizationId)
        announcementRepository.findByIdOrNull(announcementId)
            ?.takeIf { it.organization.id == (organizationId) }
            ?.apply { update(announcementReq.title, announcementReq.content) }
            ?.also { announcementRepository.save(it) }
            ?: throw BaseException(ErrorCode.ANNOUNCEMENT_NOT_FOUND, announcementId)
    }

    @Transactional
    fun delete(uid: UUID, organizationId: Long, announcementId: Long) {
        checkAuthorization(uid, organizationId)
        announcementRepository.findByIdOrNull(announcementId)
            ?.also { announcementRepository.delete(it) }
            ?: throw BaseException(ErrorCode.ANNOUNCEMENT_NOT_FOUND, announcementId)
    }

    private fun checkAuthorization(uid: UUID, organizationId: Long, ) {
        memberRepository.findByUser_UidAndOrganizationIdAndState(uid, organizationId, MemberState.JOIN)
            ?.takeIf { it.permission.equals(PermissionType.MANAGER) }
            ?: throw BaseException(ErrorCode.ANNOUNCEMENT_NOT_ALLOWED)
    }

}