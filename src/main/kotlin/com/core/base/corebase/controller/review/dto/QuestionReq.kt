package com.core.base.corebase.controller.review.dto

import com.core.base.corebase.domain.review.code.ChoiceType
import com.core.base.corebase.domain.review.code.QuestionType
import java.util.*

class QuestionReq (
    val question: String,
    val type: QuestionType,
    val choiceType: ChoiceType?,
    val limit: Int?,
    val order: Int
) 