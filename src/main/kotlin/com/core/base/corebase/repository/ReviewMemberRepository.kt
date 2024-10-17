package com.core.base.corebase.repository

import com.core.base.corebase.domain.review.ReviewMember
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ReviewMemberRepository : JpaRepository<ReviewMember, UUID> {
    fun findByReviewId(reviewId: UUID): List<ReviewMember>
    fun findByReviewIdAndMemberId(reviewId: UUID, memberId: UUID): ReviewMember?
}