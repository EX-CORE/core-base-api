package com.core.base.corebase.service.review

import com.core.base.corebase.common.exception.BaseException
import com.core.base.corebase.common.exception.code.ErrorCode
import com.core.base.corebase.controller.review.dto.*
import com.core.base.corebase.domain.review.*
import com.core.base.corebase.repository.ReviewRepository
import com.core.base.corebase.repository.ReviewerRepository
import com.core.base.corebase.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors.groupingBy
import kotlin.collections.List

@Service
class ReviewService(
    val reviewRepository: ReviewRepository,
    val reviewerRepository: ReviewerRepository,
    val userRepository: UserRepository
) {
    fun save(req: ReviewReq): Review {
        reviewRepository.save(
            Review(
                UUID.randomUUID(),
                req.title,
                req.description,
                req.surveyPeriod,
                req.reviewPeriod,
                req.companyId,
                req.sections
                    .map {
                        ReviewSection(
                            it.questions
                                .map { q ->
                                    ReviewQuestion(
                                        UUID.randomUUID(),
                                        q.question, q.type,
                                        q.limit, q.order,
                                        q.choices
                                            .map { c ->
                                                ReviewChoice(UUID.randomUUID(), c.label, c.order)
                                            }
                                    )
                                }, it.order
                        )
                    },
                req.reviewerIds,
                req.secretKey,
                req.state
            )
        ).let {
            it.reviewerIds.map {
                    uid ->
                reviewerRepository.save(Reviewer(UUID.randomUUID(), uid, uid, it.id))
            }
            return it
        }
    }

    fun get(id: UUID): ReviewRes =
        reviewRepository.findById(id)
            .map {
                it.toRes()
            }.orElseThrow { throw BaseException(ErrorCode.REVIEW_NOT_FOUND, id) }

    fun getReview(id: UUID, revieweeId: UUID): ReviewDetailRes =
        reviewRepository.findById(id)
            .orElseThrow { throw BaseException(ErrorCode.REVIEW_NOT_FOUND, id) }
            .let {
                review ->
                val reviewerId = UUID.randomUUID()
                reviewerRepository.findByReviewIdAndRevieweeIdAndReviewerId(id, revieweeId, reviewerId)
                    .orElseThrow { throw BaseException(ErrorCode.REVIEWER_NOT_ALLOWED, id) }
                review.toDetailRes(revieweeId)
            }


    fun listReviewByReviewee(id: UUID): List<ReviewerRes> =
        reviewerRepository.findByReviewerId(id)
            .map { it.toRes() }

    fun listReviewByReviewer(id: UUID): List<ReviewerRes> =
        reviewerRepository.findByRevieweeId(id)
            .stream()
            .collect(groupingBy(Reviewer::reviewId))
            .entries.stream()
            .map { it ->
                reviewRepository.findById(it.key)
                    .map { ReviewerRes(id, "", it.title, it.description, it.surveyPeriod, it.reviewPeriod, it.state) }
                    .orElseThrow { throw BaseException(ErrorCode.REVIEW_NOT_FOUND, id) }
            }
            .toList()

    private fun Reviewer.toRes(): ReviewerRes {
        val reviewee = userRepository.findByUid(revieweeId).orElseThrow()
        return reviewRepository.findById(reviewId)
            .map { ReviewerRes(id, reviewee.name, it.title, it.description,  it.surveyPeriod, it.reviewPeriod, it.state) }
            .orElseThrow { throw BaseException(ErrorCode.REVIEW_NOT_FOUND, id) }
    }


    private fun Review.toDetailRes(revieweeId: UUID): ReviewDetailRes {
        val reviewee =
            userRepository.findByUid(revieweeId)
                .orElseThrow { throw BaseException(ErrorCode.USER_NOT_FOUND, revieweeId) }
        return ReviewDetailRes(
            id,
            title,
            description,
            surveyPeriod,
            reviewPeriod,
            companyId,
            sections.map { it.toRes() },
            state,
            reviewee.name
        )
    }

    private fun Review.toRes() =
        ReviewRes(
            id,
            title,
            description,
            surveyPeriod,
            reviewPeriod,
            companyId,
            sections.map { it.toRes() },
            state,
            reviewerIds
        )

    private fun ReviewSection.toRes() =
        ReviewerSectionRes(questions.map { it.toRes() }, order)

    private fun ReviewQuestion.toRes() =
        QuestionRes(id, question, type, choices?.map { it -> it.toRes() }, limit, order)

    private fun ReviewChoice.toRes() =
        ChoiceRes(id, label, order)

}
