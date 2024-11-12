package com.core.base.corebase.domain.review

import com.core.base.corebase.domain.review.code.ReviewState
import com.core.base.corebase.domain.user.Member
import jakarta.persistence.*

@Entity(name = "review")
class Review (
   reviewMember : ReviewMember, //ReviewMember
   member: Member, // 평가자 member ID
   review : Review, //Review 넣을지 말지 고민됨.
   state: ReviewState = ReviewState.BEFORE
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L

    @Enumerated(EnumType.STRING)
    var state = state; protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_member_id", nullable = false)
    var reviewMember: ReviewMember = reviewMember; protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    var member: Member = member; protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    var review: Review = review; protected set

    @OneToMany(mappedBy = "review", cascade = [CascadeType.ALL])
    protected val mutableReviewAnswers: MutableList<ReviewAnswer> = mutableListOf()
    val answers: List<ReviewAnswer> get() = mutableReviewAnswers.toList()

}
