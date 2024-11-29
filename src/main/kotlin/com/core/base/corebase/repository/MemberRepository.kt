package com.core.base.corebase.repository

import com.core.base.corebase.domain.organization.Organization
import com.core.base.corebase.domain.user.Member
import com.core.base.corebase.domain.user.code.MemberState
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByUser_UidAndOrganizationIdAndState(uid: UUID, organizationId: Long, state: MemberState): Member?
    fun findByOrganization(organization: Organization): List<Member>
    fun findByOrganizationAndId(organization: Organization, id: Long): Optional<Member>
    fun existsByEmailAndOrganization(email: String, organization: Organization): Boolean
}