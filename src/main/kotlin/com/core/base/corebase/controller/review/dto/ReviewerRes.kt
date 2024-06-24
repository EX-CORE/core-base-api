package com.core.base.corebase.controller.review.dto

import com.core.base.corebase.domain.review.Period
import com.core.base.corebase.domain.review.code.StateType
import java.util.*

class ReviewerRes (
    val id: UUID,
    val name : String,
    val title: String,
    val description: String,
    val surveyPeriod: Period,
    val reviewPeriod: Period,
    val state: StateType
)