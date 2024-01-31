package com.core.base.corebase.domain.review

import com.core.base.corebase.domain.review.code.ChoiceType
import com.core.base.corebase.domain.review.code.QuestionType
import java.util.*


class ReviewQuestion(
    val id : UUID,
    val question: String,
    val type: QuestionType,
    val choiceType: ChoiceType?,
    val limit: Int?,
    val order: Int
)