package com.core.base.corebase.repository;

import com.core.base.corebase.domain.review.ReviewForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewFormRepository extends JpaRepository<ReviewForm, Long> {
    // Add custom query methods here if needed
}
