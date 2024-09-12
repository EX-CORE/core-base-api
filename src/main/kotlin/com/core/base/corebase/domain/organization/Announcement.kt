package com.core.base.corebase.domain.organization

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.util.*

@Document("announcement")
class Announcement(
    val organizationId: UUID,
    var title: String,
    var content: String,
    val createdAt: LocalDate = LocalDate.now(),
    @Id val id: UUID = UUID.randomUUID()
) {
}