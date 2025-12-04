package com.core.base.corebase.service.review;

import com.core.base.corebase.common.code.ErrorCode;
import com.core.base.corebase.common.exception.BaseException;
import com.core.base.corebase.domain.organization.Organization;
import com.core.base.corebase.domain.review.*;
import com.core.base.corebase.domain.review.code.QuestionType;
import com.core.base.corebase.domain.review.code.ReviewState;
import com.core.base.corebase.domain.review.code.StateType;
import com.core.base.corebase.dto.review.CreateReviewBaseRequest;
import com.core.base.corebase.dto.review.ReviewBaseResponse;
import com.core.base.corebase.repository.OrganizationRepository;
import com.core.base.corebase.repository.ReviewBaseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class ReviewBaseService {

    private final ReviewBaseRepository reviewBaseRepository;
    private final OrganizationRepository organizationRepository;

    @Transactional
    public ReviewBaseResponse createReviewBase(CreateReviewBaseRequest request) {
        // 1. 조직 조회
        Organization organization = organizationRepository.findById(request.getOrganizationId())
                .orElseThrow(() -> new BaseException(ErrorCode.ORGANIZATION_NOT_FOUND, request.getOrganizationId()));

        // 2. ReviewBase 생성
        ReviewBase reviewBase = ReviewBase.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .surveyPeriod(request.getSurveyPeriod())
                .reviewPeriod(request.getReviewPeriod())
                .organization(organization)
                .secretKey(generateSecretKey())
                .state(StateType.READY)
                .build();

        // 3. 기본 점수 선택지 추가
        AtomicInteger choiceOrder = new AtomicInteger(1);
        request.getDefaultScoreChoices().forEach(choiceDto -> {
            ReviewChoice choice = ReviewChoice.builder()
                    .label(choiceDto.getContent())
                    .orderNum(choiceOrder.getAndIncrement())
                    .score(choiceDto.getScore())
                    .reviewBase(reviewBase)
                    .build();
            reviewBase.addDefaultScoreChoice(choice);
        });

        // 4. 섹션 및 질문, 선택지 추가
        request.getSections().forEach(sectionDto -> {
            ReviewSection section = ReviewSection.builder()
                    .title(sectionDto.getTitle())
                    .description(sectionDto.getDescription())
                    .reviewBase(reviewBase)
                    .build();

            sectionDto.getQuestions().forEach(questionDto -> {
                ReviewQuestion question = ReviewQuestion.builder()
                        .question(questionDto.getContent())
                        .type(questionDto.getType())
                        .range(questionDto.getRange())
                        .orderNum(questionDto.getOrderNum())
                        .useScore(questionDto.getUseScore())
                        .useMultiSelect(questionDto.getUseMultiSelect())
                        .reviewSection(section)
                        .build();

                // Add choices if this is a choice question
                if (questionDto.getChoices() != null && !questionDto.getChoices().isEmpty()) {
                    questionDto.getChoices().forEach(choiceDto -> {
                        ReviewChoice choice = ReviewChoice.builder()
                                .label(choiceDto.getContent())
                                .orderNum(choiceDto.getOrderNum())
                                .score(choiceDto.getScore())
                                .reviewBase(reviewBase)
                                .reviewQuestion(question)
                                .build();
                        question.getChoices().add(choice);
                    });
                }
                section.getQuestions().add(question);
            });
            reviewBase.getSections().add(section);
        });

        // 5. 저장
        ReviewBase savedReviewBase = reviewBaseRepository.save(reviewBase);
        return ReviewBaseResponse.from(savedReviewBase);
    }

    private String generateSecretKey() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 20);
    }
}