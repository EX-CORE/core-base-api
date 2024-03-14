package com.core.base.corebase.controller.review.dto

import com.core.base.corebase.controller.company.dto.ProjectRes
import com.core.base.corebase.domain.review.Period
import com.core.base.corebase.domain.review.code.StateType
import java.time.LocalDate
import java.util.*

class ReviewRes(
    id: UUID,
    title: String,
    description: String,
    surveyPeriod: Period,
    reviewPeriod: Period,
    companyId: UUID,
    sections: List<ReviewerSectionRes>,
    state: StateType,
    projects : List<ProjectRes>,
    val reviewerIds: List<UUID>
) : ReviewSimpleRes(id, title, description, surveyPeriod, reviewPeriod, companyId, sections, state, projects)