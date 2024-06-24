package com.core.base.corebase.domain.company

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document("organization")
class Organization(
    @Id
    val id: UUID,
    val name: String,
    val ceo: String,
    val telNumber: String,
    val address: String,
    val teams: List<Team>
)