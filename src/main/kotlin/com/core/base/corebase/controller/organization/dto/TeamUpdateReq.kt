package com.core.base.corebase.controller.organization.dto

class TeamUpdateReq(
    val id: Long,
    val name: String,
    val order: Int,
    val parentsId: Long?
)