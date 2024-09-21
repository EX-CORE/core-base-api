package com.core.base.corebase.repository

import com.core.base.corebase.domain.user.Member
import com.core.base.corebase.domain.user.code.MemberState
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface MemberRepository : MongoRepository<Member, UUID> {
    fun findTopByEmailAndAndState(email: String, state: MemberState): Member?
    fun findByUid(uid: UUID): List<Member>
    fun findByUidAndOrganizationId(uid: UUID, organizationId: UUID): Member?
}