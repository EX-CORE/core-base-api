package com.core.base.corebase.domain.review

import com.core.base.corebase.domain.review.code.ChoiceType
import com.core.base.corebase.domain.review.code.QuestionType
import java.util.*


class ReviewChoice(
    val id : UUID,
    val label: String,
    val order: Int
)