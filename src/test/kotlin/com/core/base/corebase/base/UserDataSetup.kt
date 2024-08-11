package com.core.base.corebase.base

import com.core.base.corebase.domain.user.Account
import com.core.base.corebase.domain.user.User
import com.core.base.corebase.domain.user.code.UserState
import com.core.base.corebase.repository.AccountRepository
import com.core.base.corebase.repository.UserRepository
import com.core.base.corebase.support.JwtProvider
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserDataSetup(
    private val userRepository: UserRepository,
    private val accountRepository: AccountRepository,
    private val jwtProvider: JwtProvider
) {

    fun addUser(name: String = "홍길동", email: String = "email@email.com") =
        userRepository.save(User(UUID.randomUUID(), name, email))
            .also { accountRepository.save(Account(it.uid, "refreshToken", UserState.ACTIVE)) }

    fun getAccessToken(user: User = addUser()) = jwtProvider.generateAccessToken(user.uid)

}