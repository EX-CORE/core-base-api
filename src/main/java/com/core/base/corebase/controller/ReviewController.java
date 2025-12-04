// src/main/java/com/core/base/corebase/controller/ReviewController.java
package com.core.base.corebase.controller;

import com.core.base.corebase.domain.review.ReviewStatus;
import com.core.base.corebase.dto.review.CreateReviewRequest;
import com.core.base.corebase.dto.review.ReviewResponse;
import com.core.base.corebase.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Tag(name = "리뷰 관리", description = "리뷰 관리 API")
@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "리뷰 생성", description = "새로운 리뷰를 생성합니다.")
    @PostMapping
    public ResponseEntity<ReviewResponse> createReview(
            @Valid @RequestBody CreateReviewRequest request,
            @RequestHeader("X-User-Id") Long userId) {
        ReviewResponse response = reviewService.createReview(request, userId);
        return ResponseEntity.created(URI.create("/api/v1/reviews/" + response.id()))
                .body(response);
    }

    @Operation(summary = "리뷰 목록 조회", description = "리뷰 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<Page<ReviewResponse>> getReviews(
            @PageableDefault(size = 10) Pageable pageable,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) ReviewStatus status) {
        return ResponseEntity.ok(reviewService.getReviews(pageable, search, status));
    }

    @Operation(summary = "리뷰 상세 조회", description = "특정 리뷰의 상세 정보를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponse> getReview(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getReview(id));
    }

    @Operation(summary = "리뷰 삭제", description = "특정 리뷰를 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}