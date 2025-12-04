// src/main/java/com/core/base/corebase/repository/ReviewParticipantRepository.java
package com.core.base.corebase.repository;

import com.core.base.corebase.domain.review.ReviewParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewParticipantRepository extends JpaRepository<ReviewParticipant, Long> {
}