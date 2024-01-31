package com.core.base.corebase.controller.review

import com.core.base.corebase.controller.review.dto.ReviewRes
import com.core.base.corebase.domain.review.Review
import com.core.base.corebase.service.review.ReviewService
import org.springframework.web.bind.annotation.*
import java.util.UUID


@RestController
@RequestMapping("/review")
class ReviewController(
    val reviewService: ReviewService
) {
    @PostMapping
    fun save() : Review = reviewService.save()

    @GetMapping("/{id}")
    fun get(@PathVariable id: UUID) : ReviewRes = reviewService.get(id)

}