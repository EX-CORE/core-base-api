package com.core.base.corebase.repository

import com.core.base.corebase.domain.user.Account
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.UUID

interface AccountRepository : MongoRepository<Account, UUID> {

    fun findByUid(uuid: UUID): Account?

}