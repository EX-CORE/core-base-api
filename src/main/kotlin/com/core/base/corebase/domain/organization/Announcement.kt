package com.core.base.corebase.domain.organization

import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime
import jakarta.persistence.*

import java.util.*

@Entity(name = "announcement")
class Announcement(
    organizationId: UUID,
    title: String,
    content: String
) {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    var id: UUID = UUID.randomUUID()
    var organizationId = organizationId; protected set
    var title = title; protected set
    var content = content; protected set
    var createdAt: LocalDateTime = LocalDateTime.now();

    fun update(title: String, content: String) {
        this.title = title
        this.content = content
    }
}