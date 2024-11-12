package com.core.base.corebase.controller.organization.dto

class OrganizationRes(
    val id: Long,
    val name: String,
    val logoFileName: String?,
    val ceo: String?,
    val telNumber: String?,
    val address: String?,
    val teams: List<TeamRes>?
)