package com.core.base.corebase.controller.review

import com.core.base.corebase.domain.review.Review
import com.core.base.corebase.service.review.ReviewService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/review")
class ReviewController(
    val reviewService: ReviewService
) {
    @PostMapping
    fun save() : Review = reviewService.save()

}