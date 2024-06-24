package com.core.base.corebase.controller.company.dto

import java.util.*

class TeamRes(
    val id: UUID,
    val name: String,
    val order: Int,
    var parentsId: UUID? = null
)