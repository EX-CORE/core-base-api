package com.core.base.corebase.repository

import com.core.base.corebase.domain.user.Member
import com.core.base.corebase.domain.user.code.MemberState
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByUser_UidAndOrganizationIdAndState(uid: UUID, organizationId: Long, state: MemberState): Member?
}