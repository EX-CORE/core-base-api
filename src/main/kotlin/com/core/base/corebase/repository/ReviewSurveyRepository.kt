package com.core.base.corebase.repository

import com.core.base.corebase.domain.review.ReviewSurvey
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ReviewSurveyRepository : MongoRepository<ReviewSurvey, Long> {
    fun findByReviewIdAndReviewerId(reviewId: UUID, reviewerId: UUID): Optional<ReviewSurvey>
}