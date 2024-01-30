package com.core.base.corebase.service.review

import com.core.base.corebase.domain.review.*
import com.core.base.corebase.domain.review.code.ChoiceType
import com.core.base.corebase.domain.review.code.QuestionType
import org.springframework.stereotype.Service
import java.util.*

@Service
class ReviewService(
    val reviewRepository: ReviewRepository
) {
    fun save(): Review {
        return reviewRepository.save(
            Review(
                UUID.randomUUID(), "title", "desc",
                listOf(ReviewSection(ChoiceType.ANSWER_3,
                    listOf(ReviewQuestion("question", QuestionType.OBJECTIVE, 10, 1)),
                    1)),
                1L
            )
        );
    }
}
