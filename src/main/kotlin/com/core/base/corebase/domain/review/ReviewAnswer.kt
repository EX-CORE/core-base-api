package com.core.base.corebase.domain.review

import jakarta.persistence.*

@Entity(name = "review_answer")
class ReviewAnswer (
    review: Review,
    question: ReviewQuestion
){

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    var review: Review = review; protected set

    @OneToMany(mappedBy = "reviewAnswer", cascade = [CascadeType.ALL])
    protected val mutableReviewChoices: MutableList<ReviewChoice> = mutableListOf()
    val choices: List<ReviewChoice> get() = mutableReviewChoices.toList()

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    var question: ReviewQuestion = question; protected set

}
