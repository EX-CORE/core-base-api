package com.core.base.corebase.domain.user

import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document("accounts")
class Account(
    var uid: UUID,
    var refreshToken: String
)