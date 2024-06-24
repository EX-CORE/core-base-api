package com.core.base.corebase.domain.review

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document("reviewer")
class Reviewer(
    @Id
    val id : UUID,
    val revieweeId : UUID,
    val reviewerId : UUID,
    val reviewId: UUID,
)