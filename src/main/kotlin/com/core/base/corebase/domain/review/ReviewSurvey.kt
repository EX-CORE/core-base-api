package com.core.base.corebase.domain.review

import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document("review_survey")
class ReviewSurvey(
    val reviewId: UUID,
    val reviewerId : UUID,
    val projectIds: List<UUID>,
    val extraReviewer : UUID
) {
}