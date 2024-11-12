package com.core.base.corebase.controller.review.dto

class ReviewerSectionReq (
    val questions: List<QuestionReq>,
    val orderNum: Int,
    var name: String,
)