package com.core.base.corebase.service.user

import com.core.base.corebase.controller.user.dto.UserOrganizationRes
import com.core.base.corebase.controller.user.dto.UserOrganizationRes.InvitedUserOrganization
import com.core.base.corebase.controller.user.dto.UserOrganizationRes.ParticipationUserOrganization
import com.core.base.corebase.repository.MemberRepository
import com.core.base.corebase.repository.OrganizationRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    private val memberRepository: MemberRepository,
    private val organizationRepository: OrganizationRepository,
) {

    fun getUserOrganization(uid: UUID): UserOrganizationRes =
        memberRepository.findByUid(uid)
            .run {
                val info = this.map { it.organizationId }
                    .let { organizationRepository.findAllById(it).associateBy { it.id } }

                UserOrganizationRes(
                    filter { !it.isWait() }.let {
                        this.map {
                            val org = info.get(it.organizationId)
                            ParticipationUserOrganization(it.organizationId, org?.logoFileName, org?.name, it.permission)
                        }
                    },
                    filter { it.isWait() }.let {
                        this.map {
                            val org = info.get(it.organizationId)
                            InvitedUserOrganization(
                                it.organizationId,
                                org?.logoFileName,
                                org?.name
                            )
                        }
                    }
                )
            }

}
