package com.core.base.corebase.repository;

import com.core.base.corebase.domain.organization.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    List<Announcement> findByOrganizationIdOrderByCreatedAtDesc(Long organizationId);
}
