package com.core.base.corebase.controller.review.dto

class ReviewerSectionReq (
    val questions: List<QuestionReq>,
    val order: Int,
    var name: String,
)