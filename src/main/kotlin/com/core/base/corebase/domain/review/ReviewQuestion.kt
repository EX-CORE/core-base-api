package com.core.base.corebase.domain.review

import com.core.base.corebase.domain.review.code.QuestionType
import org.springframework.data.mongodb.core.mapping.Document


class ReviewQuestion(
    val question: String,
    val type: QuestionType,
    val limit: Int?,
    val order: Int
)