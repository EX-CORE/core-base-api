package com.core.base.corebase.domain.user

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document("user")
class User(
    @Id
    val uid: UUID,
    val email: String,
    val name: String,
    val companyId: UUID
)