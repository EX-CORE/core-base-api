package com.core.base.corebase.repository

import com.core.base.corebase.domain.review.Review
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ReviewRepository : MongoRepository<Review, Long> {
    fun findById(id: UUID): Optional<Review>
    fun findByReviewerIdsIn(reviewerId: UUID): List<Review>
}