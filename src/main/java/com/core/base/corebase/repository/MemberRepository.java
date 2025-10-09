package com.core.base.corebase.repository;

import com.core.base.corebase.domain.organization.Organization;
import com.core.base.corebase.domain.user.Member;
import com.core.base.corebase.domain.user.code.MemberState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUser_UidAndOrganizationIdAndState(UUID uid, Long organizationId, MemberState state);
    List<Member> findByOrganization(Organization organization);
    Optional<Member> findByOrganizationAndId(Organization organization, Long id);
    boolean existsByEmailAndOrganization(String email, Organization organization);
    List<Member> findByEmailAndUserIsNull(String email);
}
