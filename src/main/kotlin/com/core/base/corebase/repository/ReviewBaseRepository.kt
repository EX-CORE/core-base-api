package com.core.base.corebase.repository

import com.core.base.corebase.domain.review.ReviewBase
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ReviewBaseRepository : JpaRepository<ReviewBase, UUID> {
}