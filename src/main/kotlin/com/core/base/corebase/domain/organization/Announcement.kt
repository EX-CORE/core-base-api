package com.core.base.corebase.domain.organization

import jakarta.persistence.*
import net.huray.backend.minuting.entity.common.BaseDateTimeEntity

@Entity(name = "announcement")
class Announcement(
    organization: Organization,
    title: String,
    content: String
): BaseDateTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    var organization: Organization = organization; protected set

    var title = title; protected set
    var content = content; protected set

    fun update(title: String, content: String) {
        this.title = title
        this.content = content
    }
}