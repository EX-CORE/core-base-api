package com.core.base.corebase.controller.review.dto

import com.core.base.corebase.domain.review.Period
import com.core.base.corebase.domain.review.code.StateType
import java.util.*

class ReviewRes(
    id: UUID,
    title: String,
    description: String,
    surveyPeriod: Period,
    reviewPeriod: Period,
    organizationId: UUID,
    sections: List<ReviewerSectionRes>,
    state: StateType,
    members: List<ReviewMemberRes>?
) : ReviewSimpleRes(id, title, description, surveyPeriod, reviewPeriod, organizationId, sections, state)