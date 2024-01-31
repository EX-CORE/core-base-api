package com.core.base.corebase.domain.answer

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID


@Document("answer")
class Answer (
    @Id
    val id : UUID,
    val reviewerId : UUID,
    val answers : List<AnswerQuestion>
)
