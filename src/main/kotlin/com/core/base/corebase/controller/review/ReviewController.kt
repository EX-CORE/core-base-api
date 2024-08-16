package com.core.base.corebase.controller.review

import com.core.base.corebase.controller.review.dto.ReviewReq
import com.core.base.corebase.controller.review.dto.ReviewRes
import com.core.base.corebase.controller.review.dto.ReviewSurveyRes
import com.core.base.corebase.service.review.ReviewService
import com.core.base.corebase.service.review.SurveyService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import java.util.*


@Tag(name = "리뷰 API")
@RestController
@RequestMapping("/reviews")
class ReviewController(
    private val reviewService: ReviewService,
    private val surveyService: SurveyService
) {

    @PostMapping
    fun save(@RequestBody req: ReviewReq) = reviewService.save(req)

    @GetMapping("/{id}")
    fun get(@PathVariable id: UUID) : ReviewRes = reviewService.get(id)

//    @GetMapping("/{id}/reviewee/{uid}")
//    fun getReviewByReviewee(@PathVariable(name = "id") id: UUID, @PathVariable(name = "uid") uid: UUID) : ReviewDetailRes = reviewService.getReview(id, uid)
//
//    @GetMapping("/reviewee/{uid}")
//    fun listReviewByReviewee(@PathVariable uid: UUID) : List<ReviewerRes> = reviewService.listReviewByReviewee(uid)
//
//    @GetMapping("/reviewer/{uid}")
//    fun listReviewByReviewer(@PathVariable uid: UUID) : List<ReviewerRes> = reviewService.listReviewByReviewer(uid)

    @PostMapping("/{id}/pause")
    fun pause(@PathVariable id: UUID) = reviewService.pause(id)

    @GetMapping("/{id}/surveys")
    fun getSurvey(@PathVariable id: UUID) : ReviewSurveyRes = surveyService.get(id)

//    @PostMapping("/{id}/surveys")
//    fun saveSurvey(@PathVariable id: UUID, @RequestBody req: ReviewSurveyReq) = surveyService.save(id, req)

}