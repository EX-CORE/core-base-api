package com.core.base.corebase.repository

import com.core.base.corebase.domain.company.Organization
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface OrganizationRepository : MongoRepository<Organization, UUID> {
}