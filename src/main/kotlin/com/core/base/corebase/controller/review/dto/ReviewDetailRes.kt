package com.core.base.corebase.controller.review.dto

import com.core.base.corebase.domain.review.ReviewPeriod
import com.core.base.corebase.domain.review.code.StateType
import java.util.*

class ReviewDetailRes(
    id: UUID,
    title: String,
    description: String,
    surveyPeriod: ReviewPeriod,
    reviewPeriod: ReviewPeriod,
    organizationId: Long,
    sections: List<ReviewerSectionRes>,
    state: StateType,
    val revieweeName: String,
) : ReviewSimpleRes(id, title, description, surveyPeriod, reviewPeriod, organizationId, sections, state)