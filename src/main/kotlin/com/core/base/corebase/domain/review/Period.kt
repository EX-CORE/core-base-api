package com.core.base.corebase.domain.review

import java.time.LocalDate

class Period(
    val startDate: LocalDate,
    val endDate: LocalDate,
){
    fun isBefore(now: LocalDate) : Boolean =
            startDate.isBefore(now)
  fun between(now : LocalDate) : Boolean =
          startDate.isEqual(now)
                  || endDate.isEqual(now)
                  || (startDate.isAfter(now) && endDate.isBefore(endDate))
}