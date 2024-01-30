package com.core.base.corebase.domain.review

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "review")
class Review(
    @Id
    val id: UUID,
    val title: String,
    val description: String,
    val sections: List<ReviewSection>,
    val companyId: Long
)