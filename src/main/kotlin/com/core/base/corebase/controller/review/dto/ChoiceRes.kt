package com.core.base.corebase.controller.review.dto

import java.util.*

class ChoiceRes(
    val id: UUID,
    val label: String,
    val order: Int,
    var score: Int?
) 