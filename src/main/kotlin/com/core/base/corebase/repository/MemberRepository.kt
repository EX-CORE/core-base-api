package com.core.base.corebase.repository

import com.core.base.corebase.domain.company.Organization
import com.core.base.corebase.domain.review.ReviewBase
import com.core.base.corebase.domain.user.Member
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MemberRepository : MongoRepository<Member, Long> {
    fun findById(id: UUID): Member?
    fun findByUid(uid: UUID): List<Member>
    fun findByUidAndOrganizationId(uid: UUID, organizationId: UUID): Member?
}