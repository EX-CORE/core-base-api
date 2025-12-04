// src/main/java/com/core/base/corebase/service/ReviewService.java
package com.core.base.corebase.service;

import com.core.base.corebase.domain.review.*;
import com.core.base.corebase.domain.user.Member;
import com.core.base.corebase.dto.review.CreateReviewRequest;
import com.core.base.corebase.dto.review.ReviewResponse;
import com.core.base.corebase.repository.MemberRepository;
import com.core.base.corebase.repository.ReviewFormRepository;
import com.core.base.corebase.repository.ReviewParticipantRepository;
import com.core.base.corebase.repository.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewFormRepository reviewFormRepository;
    private final MemberRepository memberRepository;
    private final ReviewParticipantRepository reviewParticipantRepository;

    @Transactional
    public ReviewResponse createReview(CreateReviewRequest request, Long creatorId) {
        // 1. Get the review form
        ReviewForm reviewForm = reviewFormRepository.findById(request.reviewFormId())
                .orElseThrow(() -> new EntityNotFoundException("리뷰 양식을 찾을 수 없습니다."));
        
        // 2. Get creator
        Member creator = memberRepository.findById(creatorId)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다. ID: " + creatorId));
        
        // 3. Create the review
        Review review = Review.builder()
                .name(request.title())
                .startDate(request.startDate())
                .endDate(request.endDate())
                .reviewForm(reviewForm)
                .creator(creator)
                .build();
        
        // 4. Add participants
        Set<Long> participantUserIds = new HashSet<>();
        for (CreateReviewRequest.ParticipantDto participantDto : request.participants()) {
            if (participantUserIds.contains(participantDto.userId())) {
                throw new IllegalArgumentException("중복된 참여자가 존재합니다: " + participantDto.userId());
            }
            participantUserIds.add(participantDto.userId());
            
            Member participant = memberRepository.findById(participantDto.userId())
                    .orElseThrow(() -> new EntityNotFoundException("참여자를 찾을 수 없습니다. ID: " + participantDto.userId()));
            
            ReviewParticipant reviewParticipant = ReviewParticipant.builder()
                    .review(review)
                    .member(participant)
                    .role(participantDto.role())
                    .build();
            
            review.addParticipant(reviewParticipant);
        }
        
        // 5. Save the review
        Review savedReview = reviewRepository.save(review);
        return ReviewResponse.from(savedReview);
    }

    public Page<ReviewResponse> getReviews(Pageable pageable, String search, ReviewStatus status) {
        return reviewRepository.findAll(pageable)
                .map(ReviewResponse::from);
    }

    public ReviewResponse getReview(Long id) {
        return reviewRepository.findById(id)
                .map(ReviewResponse::from)
                .orElseThrow(() -> new EntityNotFoundException("리뷰를 찾을 수 없습니다."));
    }

    @Transactional
    public void deleteReview(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("리뷰를 찾을 수 없습니다."));
        reviewRepository.delete(review);
    }
}