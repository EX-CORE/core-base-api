package com.core.base.corebase.controller.organization.dto

import com.core.base.corebase.domain.user.code.MemberState
import com.core.base.corebase.domain.user.code.PermissionType

class MemberRes(
    val id: Long,
    val name: String,
    val email: String,
    val teamName: String?,
    val permission: PermissionType,
    var state: MemberState
)