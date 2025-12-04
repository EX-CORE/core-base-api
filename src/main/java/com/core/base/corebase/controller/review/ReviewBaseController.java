// ReviewBaseController.java
package com.core.base.corebase.controller.review;

import com.core.base.corebase.dto.review.CreateReviewBaseRequest;
import com.core.base.corebase.dto.review.ReviewBaseResponse;
import com.core.base.corebase.service.review.ReviewBaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "리뷰 베이스 관리", description = "리뷰 베이스 생성 및 관리 API")
@RestController
@RequestMapping("/api/v1/review-bases")
@RequiredArgsConstructor
public class ReviewBaseController {

    private final ReviewBaseService reviewBaseService;

    @Operation(summary = "리뷰 베이스 생성", 
              description = "새로운 리뷰 베이스를 생성합니다. 섹션, 질문, 선택지까지 함께 생성됩니다.")
    @PostMapping
    public ResponseEntity<ReviewBaseResponse> createReviewBase(
            @Valid @RequestBody CreateReviewBaseRequest request) {
        
        ReviewBaseResponse response = reviewBaseService.createReviewBase(request);
        return ResponseEntity.created(URI.create("/api/v1/review-bases/" + response.getId()))
                .body(response);
    }
}