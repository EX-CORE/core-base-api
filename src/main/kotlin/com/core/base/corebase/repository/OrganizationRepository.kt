package com.core.base.corebase.repository

import com.core.base.corebase.domain.organization.Organization
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface OrganizationRepository : MongoRepository<Organization, UUID> {
}