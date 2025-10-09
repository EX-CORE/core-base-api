//// Copyright 2025 Kakao Healthcare Corp. All Rights Reserved.
//
//package com.core.base.corebase.controller.review;
//
//import com.core.base.corebase.controller.review.dto.ReviewReq;
//import com.core.base.corebase.controller.review.dto.ReviewRes;
//import com.core.base.corebase.controller.review.dto.ReviewSurveyRes;
//import com.core.base.corebase.service.review.ReviewService;
//import com.core.base.corebase.service.review.SurveyService;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.UUID;
//
//@Tag(name = "리뷰 API")
//@RestController
//@RequestMapping("/reviews")
//@RequiredArgsConstructor
//public class ReviewController {
//
//    private final ReviewService reviewService;
//    private final SurveyService surveyService;
//
//    @PostMapping
//    public void save(@RequestBody ReviewReq req) {
//        reviewService.save(req);
//    }
//
//    @GetMapping("/{id}")
//    public ReviewRes get(@PathVariable Long id) {
//        return reviewService.get(id);
//    }
//
//    @PostMapping("/{id}/pause")
//    public void pause(@PathVariable UUID id) {
//        reviewService.pause(id);
//    }
//
//    @GetMapping("/{id}/surveys")
//    public ReviewSurveyRes getSurvey(@PathVariable UUID id) {
//        return surveyService.get(id);
//    }
//}
