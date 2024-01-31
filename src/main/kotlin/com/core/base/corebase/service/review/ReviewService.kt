package com.core.base.corebase.service.review

import com.core.base.corebase.controller.review.dto.QuestionRes
import com.core.base.corebase.controller.review.dto.ReviewRes
import com.core.base.corebase.controller.review.dto.ReviewerRes
import com.core.base.corebase.controller.review.dto.ReviewerSectionRes
import com.core.base.corebase.domain.review.*
import com.core.base.corebase.domain.review.code.ChoiceType
import com.core.base.corebase.domain.review.code.QuestionType
import com.core.base.corebase.domain.user.User
import com.core.base.corebase.repository.ReviewRepository
import com.core.base.corebase.repository.ReviewerRepository
import com.core.base.corebase.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.List

@Service
class ReviewService(
    val reviewRepository: ReviewRepository,
    val reviewerRepository: ReviewerRepository,
    val userRepository: UserRepository
) {
    fun save(): Review {
        reviewRepository.save(
            Review(
                UUID.randomUUID(), "title", "desc",
                listOf(
                    ReviewSection(
                        listOf(
                            ReviewQuestion(
                                UUID.randomUUID(),
                                "question",
                                QuestionType.OBJECTIVE,
                                ChoiceType.ANSWER_3,
                                10,
                                1
                            )
                        ), 1
                    )
                ),
                1L
            )
        ).let { it ->
            userRepository.findAll()
                .map { user -> reviewerRepository.save(Reviewer(UUID.randomUUID(), user.uid, user.uid, it.id)) }
            return it
        }

    }
    fun get(id: UUID): ReviewRes =
        reviewRepository.findById(id)
            .map {
                it.toRes()
            }.orElseThrow()


    fun listReview(id: UUID): List<ReviewerRes> =
        reviewerRepository.findByReviewerId(id)
            .map { it.toRes() }

    fun Reviewer.toRes(): ReviewerRes {
        var reviewee = userRepository.findByUid(revieweeId).orElseThrow()
        return reviewRepository.findById(reviewId)
            .map { ReviewerRes(id, reviewee.name, it.title, it.description) }
            .orElseThrow()
    }

    private fun Review.toRes() =
        ReviewRes(id, title, description, sections.map { it -> it.toRes() })

    private fun ReviewSection.toRes() =
        ReviewerSectionRes(questions.map { it -> it.toRes() }, order)

    private fun ReviewQuestion.toRes() =
        QuestionRes(id, question, type, choiceType, limit, order)

}
