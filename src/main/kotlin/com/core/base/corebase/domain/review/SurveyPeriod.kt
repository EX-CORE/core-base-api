package com.core.base.corebase.domain.review

import jakarta.persistence.Embeddable
import lombok.Builder
import java.time.LocalDate


@Builder
@Embeddable
class SurveyPeriod(
    val surveyStartDate: LocalDate,
    val surveyEndDate: LocalDate,
){
    fun isBefore(now: LocalDate) : Boolean = surveyStartDate.isBefore(now)

    fun between(now : LocalDate) : Boolean =
        surveyStartDate.isEqual(now)
                  || surveyEndDate.isEqual(now)
                  || (surveyStartDate.isAfter(now) && surveyEndDate.isBefore(now))

}