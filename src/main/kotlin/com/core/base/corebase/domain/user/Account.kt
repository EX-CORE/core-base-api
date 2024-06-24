package com.core.base.corebase.domain.user

import com.core.base.corebase.domain.user.code.UserState
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document("accounts")
class Account(
    var uid: UUID, //User
    var refreshToken: String,
    var state: UserState
)