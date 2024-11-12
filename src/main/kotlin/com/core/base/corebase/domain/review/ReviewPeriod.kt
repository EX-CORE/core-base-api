package com.core.base.corebase.domain.review

import jakarta.persistence.Embeddable
import lombok.Builder
import java.time.LocalDate


@Builder
@Embeddable
class ReviewPeriod(
    val reviewStartDate: LocalDate,
    val reviewEndDate: LocalDate,
){

    fun isBefore(now: LocalDate) : Boolean = reviewStartDate.isBefore(now)

    fun between(now : LocalDate) : Boolean =
        reviewStartDate.isEqual(now)
                  || reviewEndDate.isEqual(now)
                  || (reviewStartDate.isAfter(now) && reviewEndDate.isBefore(now))

}