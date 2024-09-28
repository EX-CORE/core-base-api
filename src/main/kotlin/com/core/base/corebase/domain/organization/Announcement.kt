package com.core.base.corebase.domain.organization

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Document("announcement")
class Announcement(
    val organizationId: UUID,
    var title: String,
    var content: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @Id val id: UUID = UUID.randomUUID()
) {
    fun update(title: String, content: String) {
        this.title = title
        this.content = content
    }
}