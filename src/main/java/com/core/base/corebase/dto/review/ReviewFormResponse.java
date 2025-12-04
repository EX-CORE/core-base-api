package com.core.base.corebase.dto.review;

import com.core.base.corebase.domain.review.QuestionType;
import com.core.base.corebase.domain.review.RatingOption;
import com.core.base.corebase.domain.review.ReviewForm;
import com.core.base.corebase.domain.review.ReviewFormSection;
import com.core.base.corebase.domain.review.ReviewQuestion;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ReviewFormResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private List<SectionDto> sections;

    public static ReviewFormResponse from(ReviewForm reviewForm) {
        ReviewFormResponse response = new ReviewFormResponse();
        response.setId(reviewForm.getId());
        response.setTitle(reviewForm.getTitle());
        response.setDescription(reviewForm.getDescription());
        response.setCreatedAt(reviewForm.getCreatedAt());
        
        if (reviewForm.getSections() != null) {
            response.setSections(reviewForm.getSections().stream()
                .map(SectionDto::from)
                .collect(Collectors.toList()));
        }
        
        return response;
    }

    @Getter
    @Setter
    public static class SectionDto {
        private Long id;
        private String title;
        private String description;
        private Integer displayOrder;
        private List<QuestionDto> questions;

        public static SectionDto from(ReviewFormSection section) {
            SectionDto dto = new SectionDto();
            dto.setId(section.getId());
            dto.setTitle(section.getTitle());
            dto.setDescription(section.getDescription());
            dto.setDisplayOrder(section.getDisplayOrder());
            
            if (section.getQuestions() != null) {
                dto.setQuestions(section.getQuestions().stream()
                    .map(QuestionDto::from)
                    .collect(Collectors.toList()));
            }
            
            return dto;
        }
    }

    @Getter
    @Setter
    public static class QuestionDto {
        private Long id;
        private String content;
        private String description;
        private QuestionType type;
        private Boolean required;
        private List<RatingOptionDto> ratingOptions;

        public static QuestionDto from(ReviewQuestion question) {
            QuestionDto dto = new QuestionDto();
            dto.setId(question.getId());
            dto.setContent(question.getQuestion());
            dto.setDescription(question.getDescription());
            dto.setType(QuestionType.valueOf(question.getType()));
            dto.setRequired(question.isRequired());
            
            if (question.getRatingOptions() != null) {
                dto.setRatingOptions(question.getRatingOptions().stream()
                    .map(RatingOptionDto::from)
                    .collect(Collectors.toList()));
            }
            
            return dto;
        }
    }
    // Update the RatingOptionDto in ReviewFormResponse.java
    public record RatingOptionDto(
        Long id,
        String label,
        int score
    ) {
        public static RatingOptionDto from(RatingOption option) {
            return new RatingOptionDto(
                option.getId(),
                option.getLabel(),
                option.getScore()
            );
        }
    }
}
