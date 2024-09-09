package com.core.base.corebase.domain.user

import com.core.base.corebase.domain.user.code.UserState
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document("accounts")
class Account(
    var refreshToken: String,
    var state: UserState,
    @Id val uid: UUID = UUID.randomUUID()
)