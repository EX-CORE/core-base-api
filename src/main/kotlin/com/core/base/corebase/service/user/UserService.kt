package com.core.base.corebase.service.user

import com.core.base.corebase.common.code.ErrorCode
import com.core.base.corebase.common.exception.BaseException
import com.core.base.corebase.controller.user.dto.UserReq
import com.core.base.corebase.domain.user.User
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
                User(UUID.randomUUID(), req.email, req.name)
            )
        }

    fun get(userId: UUID): User? =
        userRepository.findByUid(userId) // findById(기본?), findByUid(커스텀?)
//            ?.toRes (  userRepository.findByUid(userId).uid )
            ?: throw BaseException(ErrorCode.USER_NOT_FOUND, userId)

}
