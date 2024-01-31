package com.core.base.corebase.repository

import com.core.base.corebase.domain.review.Review
import com.core.base.corebase.domain.user.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : MongoRepository<User, Long> {
    fun findByUid(uid: UUID): Optional<User>
}