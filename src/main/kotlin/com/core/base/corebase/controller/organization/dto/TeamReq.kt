package com.core.base.corebase.controller.organization.dto

class TeamReq(
    val name: String,
    val order: Int,
    val subTeams: List<TeamReq>?
)