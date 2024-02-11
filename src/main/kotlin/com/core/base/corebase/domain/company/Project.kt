package com.core.base.corebase.domain.company

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

class Project(
    @Id
    val id: UUID,
    val name: String
)