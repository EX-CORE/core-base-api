package com.core.base.corebase.domain.review

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document("reviewer")
class Reviewer(
    @Id
    val id : UUID,
    val reviewee : String,
    val reviewer : String,
    val reviewId: UUID,
)