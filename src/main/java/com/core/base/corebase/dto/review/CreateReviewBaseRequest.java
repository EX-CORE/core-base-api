// CreateReviewBaseRequest.java
package com.core.base.corebase.dto.review;

import com.core.base.corebase.domain.review.ReviewPeriod;
import com.core.base.corebase.domain.review.SurveyPeriod;
import com.core.base.corebase.domain.review.code.QuestionType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateReviewBaseRequest {
    @NotBlank(message = "리뷰 제목은 필수 입력값입니다.")
    private String title;
    
    private String description;
    
    @Valid
    @NotNull(message = "설문 기간은 필수 입력값입니다.")
    private SurveyPeriod surveyPeriod;
    
    @Valid
    @NotNull(message = "리뷰 기간은 필수 입력값입니다.")
    private ReviewPeriod reviewPeriod;
    
    @NotNull(message = "조직 ID는 필수 입력값입니다.")
    private Long organizationId;
    
    @Valid
    @NotNull(message = "섹션 목록은 필수 입력값입니다.")
    private List<SectionDto> sections = new ArrayList<>();
    
    @Valid
    @NotNull(message = "기본 점수 선택지는 필수 입력값입니다.")
    private List<ChoiceDto> defaultScoreChoices = new ArrayList<>();

    @Getter
    @Setter
    public static class SectionDto {
        @NotBlank(message = "섹션 제목은 필수 입력값입니다.")
        private String title;
        
        private String description;
        
        @Valid
        @NotNull(message = "질문 목록은 필수 입력값입니다.")
        private List<QuestionDto> questions = new ArrayList<>();
    }

    @Getter
    @Setter
    public static class QuestionDto {
        @NotBlank(message = "질문 내용은 필수 입력값입니다.")
        private String content;
        
        @NotNull(message = "질문 유형은 필수 입력값입니다.")
        private QuestionType type;
        
        @PositiveOrZero(message = "순서는 0 이상의 숫자여야 합니다.")
        private Integer orderNum;
        
        @NotNull(message = "점수 사용 여부는 필수 입력값입니다.")
        private Boolean useScore;
        
        @NotNull(message = "다중 선택 여부는 필수 입력값입니다.")
        private Boolean useMultiSelect;
        
        private Integer range;
        
        @Valid
        private List<ChoiceDto> choices = new ArrayList<>();
    }

    @Getter
    @Setter
    public static class ChoiceDto {
        @NotBlank(message = "선택지 내용은 필수 입력값입니다.")
        private String content;
        
        @PositiveOrZero(message = "순서는 0 이상의 숫자여야 합니다.")
        private Integer orderNum;
        
        private Integer score;
    }
}