//// Copyright 2025 Kakao Healthcare Corp. All Rights Reserved.
//
//package com.core.base.corebase.service.review;
//
//import com.core.base.corebase.common.code.ErrorCode;
//import com.core.base.corebase.common.exception.BaseException;
//import com.core.base.corebase.config.AuthenticationFacade;
//import com.core.base.corebase.controller.review.dto.*;
//import com.core.base.corebase.domain.review.*;
//import com.core.base.corebase.domain.review.code.StateType;
//import com.core.base.corebase.repository.*;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class ReviewService {
//
//    private final ReviewBaseRepository reviewBaseRepository;
//    private final MemberRepository memberRepository;
//    private final ReviewMemberRepository reviewMemberRepository;
//    private final OrganizationRepository organizationRepository;
//    private final AuthenticationFacade authenticationFacade;
//    private final UserRepository userRepository;
//
//    @Transactional
//    public void save(ReviewReq req) {
//        List<ReviewSection> sections = req.getSections().stream()
//            .map(sectionReq -> {
//                List<ReviewQuestion> questions = sectionReq.getQuestions().stream()
//                    .map(q -> new ReviewQuestion(
//                        q.getQuestion(),
//                        q.getType(),
//                        q.getLimit(),
//                        q.getOrderNum(),
//                        q.getUseScore(),
//                        q.getUseMultiSelect(),
//                        null // reviewSection will be set later
//                    ))
//                    .collect(Collectors.toList());
//
//                return new ReviewSection(
//                    null, // reviewBase will be set later
//                    sectionReq.getName(),
//                    null, // description
//                    sectionReq.getOrderNum(),
//                    true // isUse
//                );
//            })
//            .collect(Collectors.toList());
//
//        var organization = organizationRepository.findById(req.getOrganizationId())
//            .orElseThrow(() -> new BaseException(ErrorCode.ORGANIZATION_NOT_FOUND, req.getOrganizationId()));
//
//        var reviewBase = new ReviewBase(
//            req.getTitle(),
//            req.getDescription(),
//            req.getSurveyPeriod(),
//            req.getReviewPeriod(),
//            organization,
//            req.getSecretKey(),
//            StateType.READY
//        );
//
//        var savedReviewBase = reviewBaseRepository.save(reviewBase);
//
//        req.getMemberIds().forEach(memberId -> {
//            var member = memberRepository.findById(memberId)
//                .orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND, memberId));
//
//            reviewMemberRepository.save(new ReviewMember(member, null));
//        });
//    }
//
//    @Transactional(readOnly = true)
//    public ReviewRes get(UUID id) {
//        var reviewBase = reviewBaseRepository.findById(id)
//            .orElseThrow(() -> new BaseException(ErrorCode.REVIEW_NOT_FOUND, id));
//
//        var reviewMembers = reviewMemberRepository.findByReviewId(id);
//
//        return toRes(reviewBase, reviewMembers);
//    }
//
//    @Transactional
//    public void pause(UUID id) {
//        var reviewBase = reviewBaseRepository.findById(id)
//            .orElseThrow(() -> new BaseException(ErrorCode.REVIEW_NOT_FOUND, id));
//
//        reviewBase.stop();
//    }
//
//    private ReviewRes toRes(ReviewBase reviewBase, List<ReviewMember> reviewMembers) {
//        return new ReviewRes(
//            reviewBase.getId(),
//            reviewBase.getTitle(),
//            reviewBase.getDescription(),
//            reviewBase.getSurveyPeriod(),
//            reviewBase.getReviewPeriod(),
//            reviewBase.getOrganization().getId(),
//            reviewBase.getSections().stream()
//                .map(this::toSectionRes)
//                .collect(Collectors.toList()),
//            reviewBase.getState(),
//            reviewMembers.stream()
//                .map(this::toMemberRes)
//                .collect(Collectors.toList())
//        );
//    }
//
//    private ReviewerSectionRes toSectionRes(ReviewSection section) {
//        return new ReviewerSectionRes(
//            section.getName(),
//            section.getQuestions().stream()
//                .map(this::toQuestionRes)
//                .collect(Collectors.toList()),
//            section.getOrderNum()
//        );
//    }
//
//    private QuestionRes toQuestionRes(ReviewQuestion question) {
//        List<ChoiceRes> choices = question.getChoices() != null
//            ? question.getChoices().stream()
//                .map(this::toChoiceRes)
//                .collect(Collectors.toList())
//            : null;
//
//        return new QuestionRes(
//            question.getId(),
//            question.getQuestion(),
//            question.getType(),
//            choices,
//            question.getRange(),
//            question.getOrderNum(),
//            question.getUseScore(),
//            question.getUseMultiSelect()
//        );
//    }
//
//    private ChoiceRes toChoiceRes(ReviewChoice choice) {
//        return new ChoiceRes(
//            choice.getId(),
//            choice.getLabel(),
//            choice.getOrderNum(),
//            choice.getScore()
//        );
//    }
//
//    private ReviewMemberRes toMemberRes(ReviewMember reviewMember) {
//        return new ReviewMemberRes(
//            reviewMember.getMember().getId(),
//            reviewMember.getMember().getName()
//        );
//    }
//}
