package com.core.base.corebase.repository

import com.core.base.corebase.domain.review.ReviewMember
import com.core.base.corebase.domain.review.ReviewPreSurvey
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ReviewMemberRepository : MongoRepository<ReviewMember, Long> {
    fun findById(id: UUID): ReviewMember?
    fun findByReviewId(reviewId: UUID): List<ReviewMember>
    fun findByReviewIdAndMemberId(reviewId: UUID, memberId: UUID): ReviewMember?
}