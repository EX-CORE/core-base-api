package com.core.base.corebase.domain.review

import com.core.base.corebase.domain.user.Member
import jakarta.persistence.*

@Entity(name = "review_member")
class ReviewMember(
    member: Member, //Member
    review: Review, // Review
){

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    var review: Review = review; protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    var member: Member = member; protected set

}