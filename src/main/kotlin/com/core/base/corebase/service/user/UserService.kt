package com.core.base.corebase.service.user

import com.core.base.corebase.controller.user.dto.UserReq
import com.core.base.corebase.domain.user.User
import com.core.base.corebase.domain.user.code.UserState
import com.core.base.corebase.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    val userRepository: UserRepository,
) {
    fun save(req: UserReq): User =
        userRepository.save(
            User(UUID.randomUUID(), req.email, req.name, req.companyId, UserState.WAIT, null)
        )
}
