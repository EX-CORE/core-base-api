package com.core.base.corebase.domain.review

import com.core.base.corebase.domain.review.code.StateType
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.util.*
import javax.crypto.SecretKey

@Document("review")
class Review(
    @Id
    val id: UUID,
    val title: String,
    val description: String,
    val surveyPeriod: Period,
    val reviewPeriod: Period,
    val companyId: UUID,
    val sections: List<ReviewSection>,
    val reviewerIds : List<UUID>,
    val secretKey: String?,
    val state: StateType
)