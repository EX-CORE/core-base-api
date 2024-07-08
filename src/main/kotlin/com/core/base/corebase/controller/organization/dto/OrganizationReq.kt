package com.core.base.corebase.controller.organization.dto

class OrganizationReq(
    val name: String,
    val ceo: String,
    val telNumber: String,
    val address: String,
    val projects: List<String>,
    val teams: List<TeamReq>
)