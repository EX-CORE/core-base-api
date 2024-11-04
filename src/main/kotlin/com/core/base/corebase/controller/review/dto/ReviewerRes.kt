package com.core.base.corebase.controller.review.dto

import com.core.base.corebase.domain.review.ReviewPeriod
import com.core.base.corebase.domain.review.code.StateType
import java.util.*

class ReviewerRes (
    val id: UUID,
    val name : String,
    val title: String,
    val description: String,
    val surveyPeriod: ReviewPeriod,
    val reviewPeriod: ReviewPeriod,
    val state: StateType
)