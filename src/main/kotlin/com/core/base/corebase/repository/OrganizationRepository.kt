package com.core.base.corebase.repository

import com.core.base.corebase.domain.organization.Organization
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface OrganizationRepository : JpaRepository<Organization, Long> {
}