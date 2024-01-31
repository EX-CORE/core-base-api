package com.core.base.corebase.controller.user.dto

import java.util.*

class UserReq(
    val email: String,
    val name: String,
    val companyId: UUID
)