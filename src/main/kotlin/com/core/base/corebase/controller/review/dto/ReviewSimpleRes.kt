package com.core.base.corebase.controller.review.dto

import com.core.base.corebase.domain.review.Period
import com.core.base.corebase.domain.review.code.StateType
import java.util.*

open class ReviewSimpleRes(
    val id: UUID,
    val title: String,
    val description: String,
    val surveyPeriod: Period,
    val reviewPeriod: Period,
    val companyId: UUID,
    val sections: List<ReviewerSectionRes>,
    val state: StateType
)