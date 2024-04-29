package com.core.base.corebase.config

import com.core.base.corebase.common.exception.BaseException
import com.core.base.corebase.common.code.ErrorCode
import org.springframework.stereotype.Component
import org.springframework.web.context.annotation.RequestScope
import java.util.*

@Component
@RequestScope
class AuthenticationFacade {

    private lateinit var _uid: UUID
    private lateinit var _email: String
    private lateinit var _name: String

    private var flag: Boolean = false

    val uid: UUID by lazy { if (flag) _uid else throw BaseException(ErrorCode.INVALID_TOKEN) }
    val email: String by lazy { if (flag) _email else throw BaseException(ErrorCode.INVALID_TOKEN) }
    val name: String by lazy { if (flag) _name else throw BaseException(ErrorCode.INVALID_TOKEN) }

    fun setInfo(uid: UUID, email: String, name: String) {
        this._uid = uid
        this._email = email
        this._name = name
        this.flag = true
    }

}