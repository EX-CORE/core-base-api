package com.core.base.corebase.controller.company.dto

import java.util.*

class CompanyRes(
    val id: UUID,
    val name: String,
    val ceo: String,
    val telNumber: String,
    val address: String,
    val projects: List<ProjectRes>,
    val teams: List<TeamRes>
)