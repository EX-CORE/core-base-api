package com.core.base.corebase.repository

import com.core.base.corebase.domain.organization.Organization
import com.core.base.corebase.domain.organization.Team
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface TeamRepository : JpaRepository<Team, Long> {
    fun findByOrganizationAndId(organization: Organization, id: Long): Optional<Team>
}