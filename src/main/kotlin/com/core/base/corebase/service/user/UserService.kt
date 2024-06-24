package com.core.base.corebase.service.user

import com.core.base.corebase.controller.user.dto.UserReq
import com.core.base.corebase.domain.user.User
import com.core.base.corebase.domain.user.code.UserState
import com.core.base.corebase.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    fun save(companyId: UUID, req: List<UserReq>): List<User> =
        req.map { req ->
            userRepository.save(
                User(UUID.randomUUID(), req.email, req.name, companyId, UserState.WAIT, req.teamId, req.permission)
            )
        }
}
