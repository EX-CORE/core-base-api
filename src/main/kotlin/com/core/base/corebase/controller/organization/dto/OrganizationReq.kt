package com.core.base.corebase.controller.organization.dto

import org.springframework.lang.Nullable
import org.springframework.web.multipart.MultipartFile

class OrganizationReq(
    val name: String,
    @Nullable
    val logo: MultipartFile?,
    @Nullable
    val ceo: String?,
    @Nullable
    val telNumber: String?,
    @Nullable
    val address: String?,
)