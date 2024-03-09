package com.core.base.corebase.controller.review.dto

import com.core.base.corebase.domain.review.code.QuestionType

class QuestionReq (
    val question: String,
    val type: QuestionType,
    val choices: List<ChoiceReq>,
    val limit: Int?,
    val order: Int,
    val useScore: Boolean,
    val useMultiSelect: Boolean,
) 