package com.core.base.corebase.domain.user

import jakarta.persistence.Id
import jakarta.persistence.Entity
import java.util.*

@Entity( name = "user")
class User(
    var name: String,
    var email: String,
    var profile: String,
    @Id val uid: UUID = UUID.randomUUID()
)