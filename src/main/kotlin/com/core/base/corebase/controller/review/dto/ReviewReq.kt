package com.core.base.corebase.controller.review.dto

import java.time.LocalDate
import java.util.*

class ReviewReq(
    val title: String,
    val description: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val companyId: UUID,
    val sections: List<ReviewerSectionReq>,
    val reviewerIds: List<UUID>
)