package com.core.base.corebase.repository

import com.core.base.corebase.domain.review.ReviewMember
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ReviewMemberRepository : MongoRepository<ReviewMember, UUID> {
    fun findByReviewId(reviewId: UUID): List<ReviewMember>
    fun findByReviewIdAndMemberId(reviewId: UUID, memberId: UUID): ReviewMember?
}