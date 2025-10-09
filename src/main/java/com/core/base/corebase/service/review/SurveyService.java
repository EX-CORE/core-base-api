//// Copyright 2025 Kakao Healthcare Corp. All Rights Reserved.
//
//package com.core.base.corebase.service.review;
//
//import com.core.base.corebase.common.code.ErrorCode;
//import com.core.base.corebase.common.exception.BaseException;
//import com.core.base.corebase.config.AuthenticationFacade;
//import com.core.base.corebase.controller.review.dto.ReviewSurveyReq;
//import com.core.base.corebase.controller.review.dto.ReviewSurveyRes;
//import com.core.base.corebase.domain.review.ReviewBase;
//import com.core.base.corebase.domain.review.ReviewPreSurvey;
//import com.core.base.corebase.repository.ReviewBaseRepository;
//import com.core.base.corebase.repository.ReviewMemberRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//public class SurveyService {
//
//    private final ReviewBaseRepository reviewBaseRepository;
//    private final ReviewMemberRepository reviewMemberRepository;
//    private final AuthenticationFacade authenticationFacade;
//
//    @Transactional(readOnly = true)
//    public ReviewSurveyRes get(UUID id) {
//        var reviewBase = reviewBaseRepository.findById(id)
//            .orElseThrow(() -> new BaseException(ErrorCode.REVIEW_NOT_FOUND, id));
//
//        var reviewMember = reviewMemberRepository.findByReviewIdAndMemberId(id, authenticationFacade.getUid())
//            .orElse(null);
//
//        var preSurvey = reviewMember != null ? reviewMember.getPreSurvey() : null;
//
//        return toRes(reviewBase, preSurvey);
//    }
//
//    @Transactional
//    public void save(UUID id, ReviewSurveyReq req) {
//        var reviewMember = reviewMemberRepository.findByReviewIdAndMemberId(id, authenticationFacade.getUid())
//            .orElseThrow(() -> new BaseException(ErrorCode.REVIEW_NOT_FOUND, id));
//
//        var preSurvey = new ReviewPreSurvey(req.getProjects(), req.getExtraReviewMember());
//        reviewMember.executePreSurvey(preSurvey);
//    }
//
//    private ReviewSurveyRes toRes(ReviewBase reviewBase, ReviewPreSurvey reviewPreSurvey) {
//        List<String> projects = reviewPreSurvey != null && reviewPreSurvey.getProjects() != null
//            ? reviewPreSurvey.getProjects()
//            : Collections.emptyList();
//
//        UUID extraReviewer = reviewPreSurvey != null
//            ? reviewPreSurvey.getExtraReviewMember()
//            : null;
//
//        return new ReviewSurveyRes(
//            reviewBase.getId(),
//            reviewBase.getTitle(),
//            reviewBase.getDescription(),
//            reviewBase.getSurveyPeriod(),
//            reviewBase.getOrganization().getId(),
//            reviewBase.getState(),
//            projects,
//            extraReviewer
//        );
//    }
//}
