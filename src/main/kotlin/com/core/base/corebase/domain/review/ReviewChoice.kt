package com.core.base.corebase.domain.review

import org.springframework.data.annotation.Id
import java.util.*

class ReviewChoice(

    val label: String,
    val order: Int,
    val score: Int?,
    @Id val id : UUID = UUID.randomUUID()
)