package com.core.base.corebase.repository;

import com.core.base.corebase.domain.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    // Add custom query methods here if needed
}
