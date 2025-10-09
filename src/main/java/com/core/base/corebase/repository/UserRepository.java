package com.core.base.corebase.repository;

import com.core.base.corebase.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUid(UUID uid);
    Optional<User> findByEmail(String email);
}
