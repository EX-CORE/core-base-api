package com.core.base.corebase.repository

import com.core.base.corebase.domain.review.ReviewBase
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ReviewBaseRepository : MongoRepository<ReviewBase, Long> {
    fun findById(id: UUID): ReviewBase?
}