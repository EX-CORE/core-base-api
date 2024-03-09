package com.core.base.corebase.controller.review.dto

import com.core.base.corebase.domain.review.code.ChoiceType
import com.core.base.corebase.domain.review.code.QuestionType
import java.util.*
import kotlin.collections.List

class QuestionRes (
    val id : UUID,
    val question: String,
    val type: QuestionType,
    val choices: List<ChoiceRes>?,
    val limit: Int?,
    val order: Int,
    var useScore: Boolean,
    var useMultiSelect: Boolean,
) 