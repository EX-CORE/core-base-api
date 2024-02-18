package com.core.base.corebase.domain.company

import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

class Team(
    var id: UUID,
    var name: String,
    var order: Int,
    var parentId: UUID? = null,
)