package com.core.base.corebase.controller.review.dto

import com.core.base.corebase.domain.review.code.ChoiceType
import com.core.base.corebase.domain.review.code.QuestionType
import java.util.*

class ChoiceRes (
    val id: UUID,
    val label: String,
    val order: Int
) 