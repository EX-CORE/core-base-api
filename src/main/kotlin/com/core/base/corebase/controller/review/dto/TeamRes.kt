package com.core.base.corebase.controller.review.dto

import java.util.*

class TeamRes(
    val id: UUID,
    val name: String,
    var parentsId: UUID? = null

)