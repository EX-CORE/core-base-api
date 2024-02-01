package com.core.base.corebase.controller.review

import com.core.base.corebase.controller.review.dto.ReviewRes
import com.core.base.corebase.controller.review.dto.ReviewerRes
import com.core.base.corebase.domain.review.Review
import com.core.base.corebase.service.review.ReviewService
import org.springframework.web.bind.annotation.*
import java.util.UUID


@RestController
@RequestMapping("/reviews")
class ReviewController(
    val reviewService: ReviewService
) {
    @PostMapping
    fun save() : Review = reviewService.save()

    @GetMapping("/{id}")
    fun get(@PathVariable id: UUID) : ReviewRes = reviewService.get(id)

    @GetMapping("/reviewee/{uid}")
    fun listReviewByReviewee(@PathVariable uid: UUID) : List<ReviewerRes> = reviewService.listReviewByReviewee(uid)

    @GetMapping("/reviewer/{uid}")
    fun listReviewByReviewer(@PathVariable uid: UUID) : List<ReviewerRes> = reviewService.listReviewByReviewer(uid)

}