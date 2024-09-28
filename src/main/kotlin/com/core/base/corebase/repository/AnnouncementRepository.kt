package com.core.base.corebase.repository

import com.core.base.corebase.domain.organization.Announcement
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface AnnouncementRepository : MongoRepository<Announcement, UUID> {
    fun findByOrganizationIdOrderByCreatedAtDesc(organizationId: UUID) : List<Announcement>
}