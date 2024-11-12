package com.core.base.corebase.controller.review.dto

import com.core.base.corebase.domain.review.ReviewPeriod
import com.core.base.corebase.domain.review.code.StateType
import java.util.*

open class ReviewSimpleRes(
    val id: UUID,
    val title: String,
    val description: String,
    val surveyPeriod: ReviewPeriod,
    val reviewPeriod: ReviewPeriod,
    val organizationId: Long,
    val sections: List<ReviewerSectionRes>,
    val state: StateType
)