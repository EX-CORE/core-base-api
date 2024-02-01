package com.core.base.corebase.domain.review

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.util.*

@Document("review")
class Review(
    @Id
    val id: UUID,
    val title: String,
    val description: String,
    val startDate : LocalDate,
    val endDate : LocalDate,
    val companyId: UUID,
    val sections: List<ReviewSection>,
    val reviewerIds : List<UUID>
)