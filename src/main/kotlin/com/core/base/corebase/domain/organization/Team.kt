package com.core.base.corebase.domain.organization

import java.util.*

class Team(
    var name: String,
    var order: Int,
    var parentId: UUID? = null,
    val id: UUID = UUID.randomUUID()
//    var leaderUId: UUID? = null,
)