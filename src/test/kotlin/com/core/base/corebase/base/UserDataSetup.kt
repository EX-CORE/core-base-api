package com.core.base.corebase.base

import com.core.base.corebase.domain.user.Account
import com.core.base.corebase.domain.user.User
import com.core.base.corebase.domain.user.code.UserState
import com.core.base.corebase.repository.AccountRepository
import com.core.base.corebase.repository.UserRepository
import com.core.base.corebase.support.JwtProvider
import org.springframework.stereotype.Component

@Component
class UserDataSetup(
    private val userRepository: UserRepository,
    private val accountRepository: AccountRepository,
    private val jwtProvider: JwtProvider
) {

    fun addUser(name: String = "홍길동", email: String = "email@email.com") =
        userRepository.save(User(name, email, ""))
            .also { accountRepository.save(Account("refreshToken", UserState.ACTIVE, it)) }

    fun getAccessToken(user: User = addUser()) = jwtProvider.generateAccessToken(user.uid)

}