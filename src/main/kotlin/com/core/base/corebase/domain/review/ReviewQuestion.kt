package com.core.base.corebase.domain.review

import com.core.base.corebase.domain.review.code.QuestionType
import org.springframework.data.annotation.Id
import java.util.*

class ReviewQuestion(
    val question: String,
    val type: QuestionType,
    val limit: Int?,
    val order: Int,
    val choices: List<ReviewChoice>?,
    val useScore: Boolean,
    val useMultiSelect: Boolean,
    @Id val id : UUID = UUID.randomUUID()
)