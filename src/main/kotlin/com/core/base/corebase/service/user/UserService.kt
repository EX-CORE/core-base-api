package com.core.base.corebase.service.user

import com.core.base.corebase.common.code.ErrorCode
import com.core.base.corebase.common.exception.BaseException
import com.core.base.corebase.controller.user.dto.UserOrganizationRes
import com.core.base.corebase.controller.user.dto.UserOrganizationRes.InvitedUserOrganization
import com.core.base.corebase.controller.user.dto.UserOrganizationRes.ParticipationUserOrganization
import com.core.base.corebase.controller.user.dto.UserRes
import com.core.base.corebase.repository.MemberRepository
import com.core.base.corebase.repository.OrganizationRepository
import com.core.base.corebase.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository,
    private val memberRepository: MemberRepository,
    private val organizationRepository: OrganizationRepository,
) {

    @Transactional(readOnly = true)
    fun getUser(uid: UUID): UserRes = userRepository.findByUid(uid)
        ?.let { UserRes(it.name, it.email, it.profile) }
        ?: throw BaseException(ErrorCode.USER_NOT_FOUND, uid)

    @Transactional(readOnly = true)
    fun getUserOrganization(uid: UUID): UserOrganizationRes =
        memberRepository.findByUid(uid)
            .run {
                val info = this.map { it.organizationId }
                    .let { organizationRepository.findAllById(it).associateBy { it.id } }

                UserOrganizationRes(
                    filter { !it.isWait() }.run { map {
                        val org = info.get(it.organizationId)
                        ParticipationUserOrganization(it.organizationId, org?.logoFileName, org?.name, it.permission)
                    } },
                    filter { it.isWait() }.run { map {
                        val org = info.get(it.organizationId)
                        InvitedUserOrganization(it.organizationId, org?.logoFileName, org?.name)
                    } }
                )
            }

}
