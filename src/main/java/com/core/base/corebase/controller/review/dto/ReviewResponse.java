// Update ReviewResponse.java
package com.core.base.corebase.controller.review.dto;

import com.core.base.corebase.domain.review.Review;
import com.core.base.corebase.domain.review.ReviewParticipant;
import com.core.base.corebase.domain.review.ReviewStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public record ReviewResponse(
    Long id,
    String name,
    ReviewStatus status,
    String reviewPeriod,
    Long reviewFormId,
    String reviewFormTitle,
    List<ParticipantResponse> participants,
    LocalDate createdAt
) {
    public static ReviewResponse from(Review review) {
        String period = String.format("%s ~ %s / %d일간",
            review.getStartDate(),
            review.getEndDate(),
            review.getStartDate().until(review.getEndDate()).getDays() + 1
        );
        
        return new ReviewResponse(
            review.getId(),
            review.getName(),
            review.getStatus(),
            period,
            review.getReviewForm().getId(),
            review.getReviewForm().getTitle(),
            review.getParticipants().stream()
                .map(ParticipantResponse::from)
                .collect(Collectors.toList()),
            review.getCreatedAt() != null ? review.getCreatedAt().toLocalDate() : LocalDate.now()
        );
    }

    public record ParticipantResponse(
        Long userId,
        String role,
        String name,
        String email
    ) {
        public static ParticipantResponse from(ReviewParticipant participant) {
            return new ParticipantResponse(
                participant.getMember().getId(),
                participant.getRole().name(),
                participant.getMember().getName(),
                participant.getMember().getEmail()
            );
        }
    }
}