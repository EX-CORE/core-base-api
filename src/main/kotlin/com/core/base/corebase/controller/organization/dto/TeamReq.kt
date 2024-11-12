package com.core.base.corebase.controller.organization.dto

import java.util.UUID

class TeamReq(
    val name: String,
    val orderNum: Int,
    val parentsId: Long?
)