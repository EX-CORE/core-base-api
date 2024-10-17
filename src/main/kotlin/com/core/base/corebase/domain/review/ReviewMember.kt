package com.core.base.corebase.domain.review

import com.core.base.corebase.domain.user.Member
import jakarta.persistence.Id
import jakarta.persistence.Entity
import java.util.*

@Entity(name = "review_member")
class ReviewMember(
    val member: Member, //Member
    val reviewId: UUID, // Review
    var preSurvey: ReviewPreSurvey?,
    @Id val id: UUID = UUID.randomUUID()
){
    fun executePreSurvey( preSurvey: ReviewPreSurvey) {
        this.preSurvey = preSurvey
    }
}