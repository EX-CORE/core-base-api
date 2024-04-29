package com.core.base.corebase.domain.review

import com.core.base.corebase.common.exception.BaseException
import com.core.base.corebase.common.code.ErrorCode
import com.core.base.corebase.domain.review.code.StateType
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.util.*

@Document("review")
class Review(
        @Id
        var id: UUID,
        var title: String,
        var description: String,
        var surveyPeriod: Period,
        var reviewPeriod: Period,
        var companyId: UUID,
        val sections: List<ReviewSection>,
        val reviewerIds : List<UUID>,
        var secretKey: String?,
        var state: StateType,
        var projectIds: List<UUID>
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