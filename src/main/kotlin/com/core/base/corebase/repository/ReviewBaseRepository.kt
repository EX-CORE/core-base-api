package com.core.base.corebase.repository

import com.core.base.corebase.domain.review.ReviewBase
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface ReviewBaseRepository : MongoRepository<ReviewBase, UUID> {
}