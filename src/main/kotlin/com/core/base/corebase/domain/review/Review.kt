package com.core.base.corebase.domain.review

import com.core.base.corebase.domain.review.code.ReviewState
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.*

@Entity(name = "review")
class Review (
    val reviewMemberId : UUID, //ReviewMember
    val memberId: UUID, // 평가자 member ID
    val reviewId : UUID, //Review 넣을지 말지 고민됨.
    val answers : List<ReviewAnswer>?,
    val state: ReviewState = ReviewState.BEFORE,
    @Id val id : UUID = UUID.randomUUID()
)
