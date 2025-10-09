package com.core.base.corebase.repository;

import com.core.base.corebase.domain.organization.Organization;
import com.core.base.corebase.domain.organization.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByOrganizationAndId(Organization organization, Long id);
}
