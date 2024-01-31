package com.core.base.corebase.controller.review.dto

import com.core.base.corebase.domain.review.ReviewSection
import org.springframework.data.annotation.Id
import java.util.*

class ReviewRes(
    val id: UUID,
    val title: String,
    val description: String,
    val sections: List<ReviewerSectionRes>
) {
}