package com.core.base.corebase.domain.review

import com.core.base.corebase.domain.user.Member
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document("review_member")
class ReviewMember(
    @Id
    val id: UUID,
    val member: Member, //Member
    val reviewId: UUID, // Review
    var preSurvey: ReviewPreSurvey?
){
    fun executePreSurvey( preSurvey: ReviewPreSurvey) {
        this.preSurvey = preSurvey
    }
}