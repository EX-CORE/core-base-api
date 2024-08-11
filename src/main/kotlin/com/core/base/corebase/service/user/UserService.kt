package com.core.base.corebase.service.user

import com.core.base.corebase.controller.user.dto.UserOrganizationRes
import com.core.base.corebase.controller.user.dto.UserReq
import com.core.base.corebase.domain.user.Member
import com.core.base.corebase.domain.user.User
import com.core.base.corebase.repository.MemberRepository
import com.core.base.corebase.repository.OrganizationRepository
import com.core.base.corebase.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository,
    private val memberRepository: MemberRepository,
    private val organizationRepository: OrganizationRepository,
) {
    fun save(organizationId: UUID, req: List<UserReq>): List<User> =
        req.map { req ->
            userRepository.save(
                User(UUID.randomUUID(), req.email, req.name)
            )
        }

    fun getUserOrganization(uid: UUID): UserOrganizationRes =
        memberRepository.findByUid(uid)
            .run { UserOrganizationRes(
                filter { !it.isWait() }.toUserOrganizationList(),
                filter { it.isWait() }.toUserOrganizationList()
            ) }

    private fun List<Member>.toUserOrganizationList() =
        this.map { it.organizationId }
            .let { organizationRepository.findAllById(it) }
            .map { UserOrganizationRes.UserOrganization(it.id, it.logoFileName, it.name) }

}
