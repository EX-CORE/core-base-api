package com.core.base.corebase.repository

import com.core.base.corebase.domain.user.Account
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface AccountRepository : JpaRepository<Account, UUID> {
    fun existsByUid(uuid: UUID): Boolean
}