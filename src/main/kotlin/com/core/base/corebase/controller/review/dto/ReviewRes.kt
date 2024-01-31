package com.core.base.corebase.controller.review.dto

import java.util.*

class ReviewRes(
    val id: UUID,
    val title: String,
    val description: String,
    val sections: List<ReviewerSectionRes>
)