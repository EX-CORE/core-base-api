// ReviewBaseResponse.java
package com.core.base.corebase.dto.review;

import com.core.base.corebase.domain.review.ReviewBase;
import com.core.base.corebase.domain.review.ReviewChoice;
import com.core.base.corebase.domain.review.ReviewPeriod;
import com.core.base.corebase.domain.review.ReviewQuestion;
import com.core.base.corebase.domain.review.ReviewSection;
import com.core.base.corebase.domain.review.SurveyPeriod;
import com.core.base.corebase.domain.review.code.QuestionType;
import com.core.base.corebase.domain.review.code.StateType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ReviewBaseResponse {
    private Long id;
    private String title;
    private String description;
    private SurveyPeriodDto surveyPeriod;
    private ReviewPeriodDto reviewPeriod;
    private Long organizationId;
    private StateType state;
    private List<SectionDto> sections;
    private List<ChoiceDto> defaultScoreChoices;

    public static ReviewBaseResponse from(ReviewBase reviewBase) {
        ReviewBaseResponse response = new ReviewBaseResponse();
        response.setId(reviewBase.getId());
        response.setTitle(reviewBase.getTitle());
        response.setDescription(reviewBase.getDescription());
        response.setSurveyPeriod(SurveyPeriodDto.from(reviewBase.getSurveyPeriod()));
        response.setReviewPeriod(ReviewPeriodDto.from(reviewBase.getReviewPeriod()));
        response.setOrganizationId(reviewBase.getOrganization().getId());
        response.setState(reviewBase.getState());

        response.setSections(reviewBase.getSections().stream()
                                       .map(SectionDto::from)
                                       .collect(Collectors.toList()));

        response.setDefaultScoreChoices(reviewBase.getDefaultScoreChoices().stream()
                                                  .map(ChoiceDto::from)
                                                  .collect(Collectors.toList()));

        return response;
    }

    @Getter
    @Setter
    public static class SurveyPeriodDto {
        private LocalDate startDate;
        private LocalDate endDate;

        public static SurveyPeriodDto from(SurveyPeriod period) {
            if (period == null) return null;
            SurveyPeriodDto dto = new SurveyPeriodDto();
            dto.setStartDate(period.getSurveyStartDate());
            dto.setEndDate(period.getSurveyEndDate());
            return dto;
        }
    }

    @Getter
    @Setter
    public static class ReviewPeriodDto {
        private LocalDate startDate;
        private LocalDate endDate;

        public static ReviewPeriodDto from(ReviewPeriod period) {
            if (period == null) return null;
            ReviewPeriodDto dto = new ReviewPeriodDto();
            dto.setStartDate(period.getReviewStartDate());
            dto.setEndDate(period.getReviewEndDate());
            return dto;
        }
    }

    @Getter
    @Setter
    public static class SectionDto {
        private Long id;
        private String title;
        private String description;
        private List<QuestionDto> questions;

        public static SectionDto from(ReviewSection section) {
            if (section == null) return null;
            SectionDto dto = new SectionDto();
            dto.setId(section.getId());
            dto.setTitle(section.getTitle());
            dto.setDescription(section.getDescription());
            dto.setQuestions(section.getQuestions().stream()
                                    .map(QuestionDto::from)
                                    .collect(Collectors.toList()));
            return dto;
        }
    }

    @Getter
    @Setter
    public static class QuestionDto {
        private Long id;
        private String question;  // Changed from content to question
        private QuestionType type;
        private Integer range;
        private Integer orderNum;
        private Boolean useScore;
        private Boolean useMultiSelect;
        private List<ChoiceDto> choices;

        public static QuestionDto from(ReviewQuestion question) {
            if (question == null) return null;
            QuestionDto dto = new QuestionDto();
            dto.setId(question.getId());
            dto.setQuestion(question.getQuestion());
            dto.setType(question.getType());
            dto.setRange(question.getRange());
            dto.setOrderNum(question.getOrderNum());
            dto.setUseScore(question.getUseScore());
            dto.setUseMultiSelect(question.getUseMultiSelect());
            dto.setChoices(question.getChoices().stream()
                                   .map(ChoiceDto::from)
                                   .collect(Collectors.toList()));
            return dto;
        }
    }

    @Getter
    @Setter
    public static class ChoiceDto {
        private Long id;
        private String label;  // Changed from content to label
        private Integer orderNum;
        private Integer score;

        public static ChoiceDto from(ReviewChoice choice) {
            if (choice == null) return null;
            ChoiceDto dto = new ChoiceDto();
            dto.setId(choice.getId());
            dto.setLabel(choice.getLabel());  // Changed from content to label
            dto.setOrderNum(choice.getOrderNum());
            dto.setScore(choice.getScore());
            return dto;
        }
    }
}