package com.core.base.corebase.controller.review.dto

import com.core.base.corebase.domain.review.ReviewPeriod
import com.core.base.corebase.domain.review.code.StateType
import java.util.*

class ReviewSurveyRes(
    val id: UUID,
    val title: String,
    val description: String,
    val surveyPeriod: ReviewPeriod,
    val organizationId: Long,
    val state: StateType,
    val projects : List<String>?,
    val extraReviewer : UUID?,
)