package com.core.base.corebase.domain.review

import org.springframework.data.annotation.Id
import java.util.*

class ReviewChoice(
    @Id
    val id : UUID,
    val label: String,
    val order: Int,
    val score: Int?
)