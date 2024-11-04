package com.core.base.corebase.repository

import com.core.base.corebase.domain.review.ReviewMember
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ReviewMemberRepository : JpaRepository<ReviewMember, Long> {
    fun findByReviewId(reviewId: Long): List<ReviewMember>
    fun findByReviewIdAndMemberId(reviewId: Long, memberId: Long): ReviewMember?
}