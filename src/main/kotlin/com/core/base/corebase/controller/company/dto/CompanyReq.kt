package com.core.base.corebase.controller.company.dto

class CompanyReq(
    val name: String,
    val ceo: String,
    val telNumber: String,
    val address: String,
    val projects: List<String>,
    val teams: List<TeamReq>
)