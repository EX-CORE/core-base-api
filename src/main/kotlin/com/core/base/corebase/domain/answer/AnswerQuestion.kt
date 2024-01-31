package com.core.base.corebase.domain.answer

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID


class AnswerQuestion (
    @Id
    val id : UUID,
    val reviewId : String,
    val questionId : UUID,
    val objectValue: Int,
    val subjectValue: String
)
