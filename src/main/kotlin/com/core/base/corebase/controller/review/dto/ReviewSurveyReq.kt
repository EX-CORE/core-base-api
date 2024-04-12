package com.core.base.corebase.controller.review.dto

import java.util.*

class ReviewSurveyReq(
    val projectIds : List<UUID>,
    val extraReviewer : UUID
)