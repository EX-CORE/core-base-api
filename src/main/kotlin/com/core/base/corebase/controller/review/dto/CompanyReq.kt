package com.core.base.corebase.controller.review.dto

import java.util.*

class CompanyReq(
    val id: UUID,
    val name: String,
    val ceo: String,
    val telNumber: String,
    val address: String,
    val projects: List<String>,
    val teams: List<TeamReq>
)