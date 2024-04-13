package com.core.base.corebase.common.exception

import com.core.base.corebase.common.code.ErrorCode
import org.springframework.http.HttpStatus

class BaseException(
    val code: HttpStatus,
    val reason: String
) : RuntimeException() {
    constructor(error: ErrorCode, value: Any? = null) : this(
        error.code,
        if (value != null) ("${error.reason} ${error.detail.replace("{VALUE}", value.toString())}") else error.reason
    )
}