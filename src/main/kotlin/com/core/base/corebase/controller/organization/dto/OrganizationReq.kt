package com.core.base.corebase.controller.organization.dto

import org.springframework.web.multipart.MultipartFile

class OrganizationReq(
    val name: String,
    val logo: MultipartFile?,
    val ceo: String?,
    val telNumber: String?,
    val address: String?,
    val projects: List<String>?,
    val teams: List<TeamReq>?
)