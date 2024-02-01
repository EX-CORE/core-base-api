package com.core.base.corebase.controller.review.dto

import java.time.LocalDate
import java.util.*

class ReviewRes(
    val id: UUID,
    val title: String,
    val description: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val sections: List<ReviewerSectionRes>
)