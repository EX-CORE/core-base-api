package com.core.base.corebase.domain.review

import jakarta.persistence.*

@Entity(name = "review_section")
class ReviewSection(
   name: String,
   order: Int,
   reviewBase: ReviewBase
){

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   var id: Long = 0L

   var name = name; protected set

   var order = order; protected set

   @OneToMany(mappedBy = "reviewSection", cascade = [CascadeType.ALL])
   protected val mutableReviewQuestions: MutableList<ReviewQuestion> = mutableListOf()
   val questions: List<ReviewQuestion> get() = mutableReviewQuestions.toList()

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "review_base_id", nullable = false)
   var reviewBase: ReviewBase = reviewBase; protected set
}