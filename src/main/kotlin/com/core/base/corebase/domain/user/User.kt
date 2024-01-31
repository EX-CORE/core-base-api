package com.core.base.corebase.domain.user

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
class User (

    @Id
    val email: String,
    val name: String

    )