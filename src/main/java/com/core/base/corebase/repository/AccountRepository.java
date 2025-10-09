package com.core.base.corebase.repository;

import com.core.base.corebase.domain.user.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    boolean existsByUid(UUID uuid);
}
