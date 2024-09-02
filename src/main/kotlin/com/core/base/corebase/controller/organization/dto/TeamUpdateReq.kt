package com.core.base.corebase.controller.organization.dto

import java.util.UUID

class TeamUpdateReq(
    val id: UUID,
    val name: String,
    val order: Int,
    val parentsId: UUID?
)