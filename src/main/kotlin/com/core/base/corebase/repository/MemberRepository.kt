package com.core.base.corebase.repository

import com.core.base.corebase.domain.user.Member
import com.core.base.corebase.domain.user.code.MemberState
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MemberRepository : MongoRepository<Member, Long> {
    fun findTopByEmailAndAndState(email: String, state: MemberState): Member?
    fun findById(id: UUID): Member?
    fun findByUid(uid: UUID): List<Member>
    fun findByUidAndOrganizationId(uid: UUID, organizationId: UUID): Member?
}