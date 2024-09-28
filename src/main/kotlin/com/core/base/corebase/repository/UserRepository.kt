package com.core.base.corebase.repository

import com.core.base.corebase.domain.user.User
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface UserRepository : MongoRepository<User, UUID> {
    fun findByUid(uid: UUID): User?
    fun findByEmail(email: String): User?
}