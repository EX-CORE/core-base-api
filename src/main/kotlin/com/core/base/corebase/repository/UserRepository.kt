package com.core.base.corebase.repository

import com.core.base.corebase.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, UUID> {
    fun findByUid(uid: UUID): User?
    fun findByEmail(email: String): User?
}