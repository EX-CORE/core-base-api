package com.core.base.corebase.domain.review

import jakarta.persistence.Id
import java.util.*

class ReviewChoice(

    val label: String,
    val order: Int,
    val score: Int?,
    @Id val id : UUID = UUID.randomUUID()
)