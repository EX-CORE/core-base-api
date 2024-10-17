package com.core.base.corebase.domain.review

import com.core.base.corebase.common.exception.BaseException
import com.core.base.corebase.common.code.ErrorCode
import com.core.base.corebase.domain.review.code.StateType
import jakarta.persistence.Id
import jakarta.persistence.Entity
import java.time.LocalDate
import java.util.*

@Entity(name = "review_base")
class ReviewBase(
    var title: String,
    var description: String,
    var surveyPeriod: Period,
    var reviewPeriod: Period,
    var organizationId: UUID, //Organization
    val sections: List<ReviewSection>,
    var secretKey: String?,
    var state: StateType,
    var defaultScoreChoices: List<ReviewChoice>,
    @Id val id: UUID = UUID.randomUUID()
) {

    fun pause() {
        if (!validPause())
            throw BaseException(ErrorCode.REVIEW_NOT_FOUND, id)
        this.state = StateType.PAUSE;
    }

    private fun validPause() : Boolean {
        return if (state.isInActive()) false
        else LocalDate.now().let {
            this.surveyPeriod.isBefore(it) || this.surveyPeriod.between(it)
        }
    }

}