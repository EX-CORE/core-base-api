package com.core.base.corebase.controller.review.dto

import com.core.base.corebase.domain.review.Period
import com.core.base.corebase.domain.review.ReviewChoice
import com.core.base.corebase.domain.review.code.StateType
import java.util.*

class ReviewReq(
    val title: String,
    val description: String,
    val surveyPeriod: Period,
    val reviewPeriod: Period,
    val companyId: UUID,
    val sections: List<ReviewerSectionReq>,
    val memberIds: List<UUID>,
    val secretKey: String,
    val projectIds: List<UUID>,
    val defaultScoreChoices: List<ReviewChoice>
)