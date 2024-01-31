package com.core.base.corebase.controller.review.dto

import com.core.base.corebase.domain.review.ReviewQuestion
import com.core.base.corebase.domain.review.ReviewSection
import java.util.*

class ReviewerSectionRes (
    val questions: List<QuestionRes>,
    val order: Int
)