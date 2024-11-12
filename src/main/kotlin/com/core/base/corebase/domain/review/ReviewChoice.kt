package com.core.base.corebase.domain.review

import jakarta.persistence.*
import java.util.*

@Entity(name = "review_choice")
class ReviewChoice(
   label: String,
   orderNum: Int,
   score: Int?,
   reviewAnswer: ReviewAnswer,
   reviewBase: ReviewBase
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L

    var label = label; protected set
    var orderNum = orderNum; protected set
    var score = score; protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_answer_id", nullable = false)
    var reviewAnswer: ReviewAnswer = reviewAnswer; protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_base_id", nullable = false)
    var reviewBase: ReviewBase = reviewBase; protected set


}