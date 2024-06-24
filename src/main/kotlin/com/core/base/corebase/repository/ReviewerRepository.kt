package com.core.base.corebase.repository

import com.core.base.corebase.domain.review.Reviewer
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ReviewerRepository : MongoRepository<Reviewer, Long> {
    fun findById(id: UUID): Reviewer?
    fun findByReviewerId(reviewerId: UUID): List<Reviewer>
    fun findByRevieweeId(revieweeId: UUID): List<Reviewer>
    fun findByReviewIdAndRevieweeIdAndReviewerId( reviewId: UUID, revieweeId: UUID, reviewerId: UUID): Reviewer?
}