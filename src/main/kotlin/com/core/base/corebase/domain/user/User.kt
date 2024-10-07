package com.core.base.corebase.domain.user

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document("user")
class User(
    var name: String,
    var email: String,
    var profile: String,
    @Id val uid: UUID = UUID.randomUUID()
)