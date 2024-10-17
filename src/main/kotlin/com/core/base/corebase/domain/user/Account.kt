package com.core.base.corebase.domain.user

import com.core.base.corebase.domain.user.code.UserState
import jakarta.persistence.Id
import jakarta.persistence.Entity
import java.util.*

@Entity( name = "accounts")
class Account(
    var refreshToken: String,
    var state: UserState,
    @Id val uid: UUID = UUID.randomUUID()
)