package com.core.base.corebase.repository

import com.core.base.corebase.domain.user.Member
import com.core.base.corebase.domain.user.code.MemberState
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MemberRepository : JpaRepository<Member, UUID> {
    fun findTopByEmailAndAndState(email: String, state: MemberState): Member?
    fun findByUid(uid: UUID): List<Member>
    fun findByUidAndOrganizationIdAndState(uid: UUID, organizationId: UUID, state: MemberState): Member?
}