package com.core.base.corebase.domain.review

import java.util.*

class ReviewChoice(
    val id : UUID,
    val label: String,
    val order: Int,
    val score: Int?
)