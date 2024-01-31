package com.core.base.corebase.service.review

import com.core.base.corebase.controller.review.dto.QuestionRes
import com.core.base.corebase.controller.review.dto.ReviewRes
import com.core.base.corebase.controller.review.dto.ReviewerSectionRes
import com.core.base.corebase.domain.review.*
import com.core.base.corebase.domain.review.code.ChoiceType
import com.core.base.corebase.domain.review.code.QuestionType
import com.core.base.corebase.repository.ReviewRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class ReviewService(
    val reviewRepository: ReviewRepository
) {
    fun save(): Review =
        reviewRepository.save(
            Review(
                UUID.randomUUID(), "title", "desc",
                listOf(ReviewSection(
                    listOf(ReviewQuestion(UUID.randomUUID(), "question", QuestionType.OBJECTIVE, ChoiceType.ANSWER_3,10, 1)), 1)),
                1L
            )
        )

    fun get(id: UUID): ReviewRes =
        reviewRepository.findById(id)
            .map { it ->
                it.toRes()
            }.orElseThrow()


    private fun Review.toRes() =
        ReviewRes(id, title, description, sections.map { it -> it.toRes() })

    private fun ReviewSection.toRes() =
        ReviewerSectionRes(questions.map { it -> it.toRes() }, order)

    private fun ReviewQuestion.toRes() =
        QuestionRes(id, question, type, choiceType, limit, order)

}
