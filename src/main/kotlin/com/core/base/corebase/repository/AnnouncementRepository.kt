package com.core.base.corebase.repository

import com.core.base.corebase.domain.organization.Announcement
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface AnnouncementRepository : JpaRepository<Announcement, UUID> {
    fun findByOrganizationIdOrderByCreatedAtDesc(organizationId: UUID) : List<Announcement>
}