package com.core.base.corebase.controller.review.dto

import java.util.*

class ReviewSurveyReq(
    val projects : List<String>,
    val extraReviewMember : UUID
)