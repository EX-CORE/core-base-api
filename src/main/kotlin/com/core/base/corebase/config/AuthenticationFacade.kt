package com.core.base.corebase.config

import com.core.base.corebase.common.exception.BaseException
import com.core.base.corebase.common.code.ErrorCode
import org.springframework.stereotype.Component
import org.springframework.web.context.annotation.RequestScope
import java.util.*

@Component
@RequestScope
class AuthenticationFacade {

    var uid: UUID = UUID.randomUUID()
        get() = if (flag) field else throw BaseException(ErrorCode.INVALID_TOKEN)
    var email: String = ""
        get() = if (flag) field else throw BaseException(ErrorCode.INVALID_TOKEN)
    var name: String = ""
        get() = if (flag) field else throw BaseException(ErrorCode.INVALID_TOKEN)

    private var flag: Boolean = false

    fun setInfo(uid: UUID, email: String, name: String) {
        this.uid = uid
        this.email = email
        this.name = name
        this.flag = true
    }
}