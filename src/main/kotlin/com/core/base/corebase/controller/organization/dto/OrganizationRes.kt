package com.core.base.corebase.controller.organization.dto

import java.util.*

class OrganizationRes(
    val id: UUID,
    val name: String,
    val logoFileName: String?,
    val ceo: String?,
    val telNumber: String?,
    val address: String?,
    val teams: List<TeamRes>?
)