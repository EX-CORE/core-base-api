package com.core.base.corebase.controller.review

import com.core.base.corebase.controller.review.dto.*
import com.core.base.corebase.domain.review.Review
import com.core.base.corebase.service.review.ReviewService
import com.core.base.corebase.service.review.SurveyService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import java.util.*


@Tag(name = "리뷰 API")
@RestController
@RequestMapping("/reviews")
class ReviewController(
    val reviewService: ReviewService,
    val surveyService: SurveyService
) {

    @PostMapping
    fun save(@RequestBody req: ReviewReq) : Review = reviewService.save(req)

    @GetMapping("/{id}")
    fun get(@PathVariable id: UUID) : ReviewRes = reviewService.get(id)

    @GetMapping("/{id}/reviewee/{uid}")
    fun getReviewByReviewee(@PathVariable(name = "id") id: UUID, @PathVariable(name = "uid") uid: UUID) : ReviewDetailRes = reviewService.getReview(id, uid)

    @GetMapping("/reviewee/{uid}")
    fun listReviewByReviewee(@PathVariable uid: UUID) : List<ReviewerRes> = reviewService.listReviewByReviewee(uid)

    @GetMapping("/reviewer/{uid}")
    fun listReviewByReviewer(@PathVariable uid: UUID) : List<ReviewerRes> = reviewService.listReviewByReviewer(uid)

    @PostMapping("/{id}/pause")
    fun pause(@PathVariable id: UUID) = reviewService.pause(id)

    @GetMapping("/{id}/surveys")
    fun getSurvey(@PathVariable id: UUID) : ReviewSurveyRes = surveyService.get(id)

    @PostMapping("/{id}/surveys")
    fun saveSurvey(@PathVariable id: UUID, @RequestBody req: ReviewSurveyReq) = surveyService.save(id, req)

}