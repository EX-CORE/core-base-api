package com.core.base.corebase.dto.review;

import com.core.base.corebase.domain.review.QuestionType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateReviewFormRequest {
    @NotBlank(message = "리뷰 제목은 필수 입력값입니다.")
    private String title;
    
    private String description;
    
    @Valid
    @NotNull(message = "섹션은 최소 1개 이상이어야 합니다.")
    private List<@Valid SectionDto> sections;

    @Getter
    @Setter
    public static class SectionDto {
        @NotBlank(message = "섹션 제목은 필수 입력값입니다.")
        private String title;
        
        private String description;
        
        @Valid
        @NotNull(message = "문항은 최소 1개 이상이어야 합니다.")
        private List<@Valid QuestionDto> questions;
    }

    @Getter
    @Setter
    public static class QuestionDto {
        @NotBlank(message = "질문 내용은 필수 입력값입니다.")
        private String content;
        
        private String description;
        
        @NotNull(message = "질문 유형은 필수 선택값입니다.")
        private QuestionType type;
        
        @NotNull(message = "필수 여부는 필수 선택값입니다.")
        private Boolean required;
        
        private List<@Valid RatingOptionDto> ratingOptions;
    }

    @Getter
    @Setter
    public static class RatingOptionDto {
        @NotBlank(message = "등급명은 필수 입력값입니다.")
        private String label;
        
        @NotNull(message = "점수는 필수 입력값입니다.")
        private Integer score;
        
        private String description;
    }
}
