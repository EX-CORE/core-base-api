package com.core.base.corebase.controller.review.dto

import com.core.base.corebase.domain.review.ReviewSection
import java.util.*

class ReviewerReq (
    val id: UUID,
    val name : String,
    val title: String,
    val description: String,
    val sections: List<ReviewSection>
) {
}