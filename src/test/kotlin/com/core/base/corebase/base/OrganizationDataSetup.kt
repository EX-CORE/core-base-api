package com.core.base.corebase.base

import com.core.base.corebase.domain.organization.Organization
import com.core.base.corebase.domain.user.Member
import com.core.base.corebase.domain.user.User
import com.core.base.corebase.domain.user.code.MemberState
import com.core.base.corebase.domain.user.code.PermissionType
import com.core.base.corebase.repository.MemberRepository
import com.core.base.corebase.repository.OrganizationRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class OrganizationDataSetup(
    private val memberRepository: MemberRepository,
    private val organizationRepository: OrganizationRepository,
    private val userDataSetup: UserDataSetup
) {

    fun addOrganization(name: String = "활빈당") =
        organizationRepository.save(Organization(UUID.randomUUID(), name, "logo.png", "ceo", "telNumber", "address", emptyList()))

    fun addMember(user: User = userDataSetup.addUser(), organization: Organization = addOrganization(),
                  state: MemberState = MemberState.JOIN, permissionType: PermissionType = PermissionType.REVIEWER) =
        memberRepository.save(Member(UUID.randomUUID(), user.email, user.name, user.uid,
            organization.id, null, state, permissionType))

}