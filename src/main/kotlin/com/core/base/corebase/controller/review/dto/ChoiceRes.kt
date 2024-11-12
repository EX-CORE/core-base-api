package com.core.base.corebase.controller.review.dto

import java.util.*

class ChoiceRes(
    val id: UUID,
    val label: String,
    val orderNum: Int,
    var score: Int?
) 