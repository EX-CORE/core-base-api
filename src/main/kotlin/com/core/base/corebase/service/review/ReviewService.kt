package com.core.base.corebase.service.review

import com.core.base.corebase.common.code.ErrorCode
import com.core.base.corebase.common.exception.BaseException
import com.core.base.corebase.config.AuthenticationFacade
import com.core.base.corebase.controller.company.dto.ProjectRes
import com.core.base.corebase.controller.review.dto.*
import com.core.base.corebase.domain.review.*
import com.core.base.corebase.repository.CompanyRepository
import com.core.base.corebase.repository.ReviewRepository
import com.core.base.corebase.repository.ReviewerRepository
import com.core.base.corebase.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import java.util.stream.Collectors.groupingBy

@Service
class ReviewService(
    val reviewRepository: ReviewRepository,
    val reviewerRepository: ReviewerRepository,
    val companyRepository: CompanyRepository,
    val authenticationFacade: AuthenticationFacade,
    val userRepository: UserRepository
) {
    fun save(req: ReviewReq): Review {
        val section = req.sections
            .map {
                ReviewSection(
                    it.name,
                    it.questions.map { q ->
                        ReviewQuestion(
                            UUID.randomUUID(),
                            q.question, q.type,
                            q.limit, q.order,
                            q.choices.map { c -> ReviewChoice(UUID.randomUUID(), c.label, c.order, c.score) },
                            q.useScore,
                            q.useMultiSelect
                        )
                    },
                    it.order
                )
            };

        val review = Review(
            UUID.randomUUID(),
            req.title,
            req.description,
            req.surveyPeriod,
            req.reviewPeriod,
            req.companyId,
            section,
            req.reviewerIds,
            req.secretKey,
            req.state,
            req.projectIds
        )

        return reviewRepository.save(review)
            .apply {
                reviewerIds.forEach { uid ->
                    reviewerRepository.save(Reviewer(UUID.randomUUID(), uid, uid, id))
                }
            }
    }

    fun get(id: UUID): ReviewRes =
        reviewRepository.findById(id)
            ?.toRes()
            ?: throw BaseException(ErrorCode.REVIEW_NOT_FOUND, id)

    fun getReview(id: UUID, revieweeId: UUID): ReviewDetailRes =
        reviewRepository.findById(id)
            ?.let {
                val reviewerId = UUID.randomUUID()
                reviewerRepository.findByReviewIdAndRevieweeIdAndReviewerId(id, revieweeId, reviewerId)
                    ?: throw BaseException(ErrorCode.REVIEWER_NOT_ALLOWED, id)
                it.toDetailRes(revieweeId)
            }
            ?: throw BaseException(ErrorCode.REVIEW_NOT_FOUND, id)


    fun listReviewByReviewee(id: UUID): List<ReviewerRes> =
        reviewerRepository.findByReviewerId(id).map { it.toRes() }

    fun listReviewByReviewer(id: UUID): List<ReviewerRes> =
        reviewerRepository.findByRevieweeId(id)
            .stream()
            .collect(groupingBy(Reviewer::reviewId))
            .entries.stream()
            .map { it ->
                reviewRepository.findById(it.key)
                    ?.let { ReviewerRes(id, "", it.title, it.description, it.surveyPeriod, it.reviewPeriod, it.state) }
                    ?: throw BaseException(ErrorCode.REVIEW_NOT_FOUND, id)
            }
            .toList()

    @Transactional
    fun pause(id: UUID) : Unit =
            reviewRepository.findById(id)
                ?.let { it.pause() }
                ?: throw BaseException(ErrorCode.REVIEW_NOT_FOUND, id)


    private fun Reviewer.toRes(): ReviewerRes {
        val reviewee = userRepository.findByUid(revieweeId)!!
        return reviewRepository.findById(reviewId)
            ?.let { ReviewerRes(id, reviewee.name, it.title, it.description,  it.surveyPeriod, it.reviewPeriod, it.state) }
            ?: throw BaseException(ErrorCode.REVIEW_NOT_FOUND, id)
    }


    private fun Review.toDetailRes(revieweeId: UUID): ReviewDetailRes {
        val reviewee = userRepository.findByUid(revieweeId)
            ?: throw BaseException(ErrorCode.USER_NOT_FOUND, revieweeId)

        return ReviewDetailRes(
            id,
            title,
            description,
            surveyPeriod,
            reviewPeriod,
            companyId,
            sections.map { it.toRes() },
            state,
            getProjects(companyId, projectIds),
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
            getProjects(companyId, projectIds),
            reviewerIds,
        )

    private fun ReviewSection.toRes() =
        ReviewerSectionRes(name, questions.map { it.toRes() }, order)

    private fun ReviewQuestion.toRes() =
        QuestionRes(id, question, type, choices?.map { it.toRes() }, limit, order, useScore, useMultiSelect)

    private fun ReviewChoice.toRes() =
        ChoiceRes(id, label, order, score)

    private fun getProjects(companyId: UUID, ids: List<UUID>) =
        companyRepository.findById(companyId)
            .orElseThrow{ throw BaseException(ErrorCode.COMPANY_NOT_FOUND, companyId) }
            .let {
                it.projects.stream()
                    .filter { project -> ids.contains(project.id) }
                    .toList()
            }.map { ProjectRes(it.id, it.name) }
}
