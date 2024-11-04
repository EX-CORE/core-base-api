package com.core.base.corebase.controller.organization.dto

import java.util.*

class TeamRes(
    val id: Long,
    val name: String,
    val order: Int,
    var parentsId: Long? = null
)