package com.core.base.corebase.controller.review.dto

class ReviewerSectionRes (
    var name: String,
    val questions: List<QuestionRes>,
    val orderNum: Int
)