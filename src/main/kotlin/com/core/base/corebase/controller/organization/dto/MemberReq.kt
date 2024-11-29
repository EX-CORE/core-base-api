package com.core.base.corebase.controller.organization.dto

import com.core.base.corebase.domain.user.code.PermissionType

class MemberReq(
    val name: String,
    val email: String,
    val teamId: Long?,
    val permission: PermissionType
)