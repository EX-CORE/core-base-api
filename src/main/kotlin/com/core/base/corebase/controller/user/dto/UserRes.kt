package com.core.base.corebase.controller.user.dto

import java.util.*

class UserRes(
    val id: UUID,
    val order: Int,
    var name: String,
    var email: String
)