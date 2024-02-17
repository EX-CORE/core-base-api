package com.core.base.corebase.domain.user

import com.core.base.corebase.domain.user.code.UserState
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document("user")
class User(
    @Id
    val uid: UUID,
    val name: String,
    val email: String,
    val companyId: UUID,
    val state: UserState = UserState.WAIT,
    val refreshToken: String?,
    val teamId: UUID,
)