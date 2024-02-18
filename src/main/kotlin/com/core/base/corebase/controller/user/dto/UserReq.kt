package com.core.base.corebase.controller.user.dto

import com.core.base.corebase.domain.user.code.PermissionType
import java.util.*

class UserReq(
    val email: String,
    val name: String,
    val companyId: UUID,
    val teamId: UUID,
    val permission: PermissionType
)