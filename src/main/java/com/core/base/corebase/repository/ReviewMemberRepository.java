package com.core.base.corebase.repository;

import com.core.base.corebase.domain.review.ReviewMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewMemberRepository extends JpaRepository<ReviewMember, Long> {
    List<ReviewMember> findByReviewId(Long reviewId);
    Optional<ReviewMember> findByReviewIdAndMemberId(Long reviewId, Long memberId);
}
