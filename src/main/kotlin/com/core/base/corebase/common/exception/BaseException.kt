package com.core.base.corebase.common.exception

import com.core.base.corebase.common.code.ErrorCode

class BaseException (
    val errorCode: ErrorCode,
    value: Any? = null
) : RuntimeException() {
    val reason: String =
        if (value != null) ("${errorCode.reason} ${errorCode.detail.replace("{VALUE}", value.toString())}")
        else errorCode.reason
}