package com.core.base.corebase.controller.company.dto

class TeamReq(
    val name: String,
    val order: Int,
    val subTeams: List<TeamReq>?
)