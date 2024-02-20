package com.core.base.corebase.domain.company

import java.util.*

class Team(
    var id: UUID,
    var name: String,
    var order: Int,
    var parentId: UUID? = null,
//    var leaderUId: UUID? = null,
)