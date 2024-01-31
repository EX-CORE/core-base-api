package com.core.base.corebase.domain.review

import com.core.base.corebase.domain.review.code.ChoiceType
import org.springframework.aot.hint.TypeReference


class ReviewSection(
   val questions: List<ReviewQuestion>,
   val order: Int
)