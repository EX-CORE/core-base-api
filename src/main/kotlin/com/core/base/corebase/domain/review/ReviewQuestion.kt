package com.core.base.corebase.domain.review

import com.core.base.corebase.domain.review.code.QuestionType
import jakarta.persistence.*
import java.util.*

@Entity(name = "review_question")
class ReviewQuestion(
    question: String,
    type: QuestionType,
    limit: Int?,
    order: Int,
    useScore: Boolean,
    useMultiSelect: Boolean,
    reviewSection: ReviewSection
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L

    var question = question; protected set
    @Enumerated(EnumType.STRING)
    var type = type; protected set
    var limit = limit; protected set
    var order = order; protected set
    var useScore = useScore; protected set
    var useMultiSelect = useMultiSelect; protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_section_id", nullable = false)
    var reviewSection: ReviewSection = reviewSection; protected set

}