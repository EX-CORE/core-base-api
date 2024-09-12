package com.core.base.corebase.service.organization

import com.core.base.corebase.common.code.ErrorCode
import com.core.base.corebase.common.exception.BaseException
import com.core.base.corebase.controller.organization.dto.AnnouncementReq
import com.core.base.corebase.controller.organization.dto.AnnouncementRes
import com.core.base.corebase.domain.organization.Announcement
import com.core.base.corebase.domain.user.code.PermissionType
import com.core.base.corebase.repository.AnnouncementRepository
import com.core.base.corebase.repository.MemberRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class AnnouncementService(
    private val memberRepository: MemberRepository,
    private val announcementRepository: AnnouncementRepository
) {

    @Transactional
    fun getList(uid: UUID, organizationId: UUID): List<AnnouncementRes> {
        memberRepository.findByUidAndOrganizationId(uid, organizationId) ?: throw BaseException(ErrorCode.INVALID_TOKEN)

        return organizationId
            .let { announcementRepository.findByOrganizationIdOrderByCreatedAtDesc(it) }
            .map { it.toRes() }
    }

    private fun Announcement.toRes(): AnnouncementRes = AnnouncementRes(id, title, content, createdAt)

    @Transactional
    fun save(announcementReq: AnnouncementReq, uid: UUID, organizationId: UUID) {
        memberRepository.findByUidAndOrganizationId(uid, organizationId)
            ?.takeIf { it.permission.equals(PermissionType.MANAGER) }
            ?: throw BaseException(ErrorCode.INVALID_TOKEN)

        announcementRepository.save(Announcement(organizationId, announcementReq.title, announcementReq.content));
    }

    @Transactional
    fun update(announcementReq: AnnouncementReq, uid: UUID, organizationId: UUID, announcementId: UUID) {
        memberRepository.findByUidAndOrganizationId(uid, organizationId)
            ?.takeIf { it.permission.equals(PermissionType.MANAGER) }
            ?: throw BaseException(ErrorCode.INVALID_TOKEN)

        announcementRepository.findByIdOrNull(announcementId)
            ?.takeIf { it.organizationId.equals(organizationId) }
            ?.update(announcementReq.title, announcementReq.content)
            ?: throw BaseException(ErrorCode.ANNOUNCEMENT_NOT_FOUND, announcementId)
    }

}