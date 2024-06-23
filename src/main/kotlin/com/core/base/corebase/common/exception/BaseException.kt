package com.core.base.corebase.common.exception

import com.core.base.corebase.common.code.ErrorCode
import org.springframework.http.HttpStatus

class BaseException (
    error: ErrorCode, value: Any? = null
) : RuntimeException() {

    private val code: HttpStatus = error.code
    private val reason: String =
        if (value != null) ("${error.reason} ${error.detail.replace("{VALUE}", value.toString())}")
        else error.reason

}