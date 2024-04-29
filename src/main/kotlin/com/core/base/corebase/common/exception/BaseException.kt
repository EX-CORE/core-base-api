package com.core.base.corebase.common.exception

import com.core.base.corebase.common.code.ErrorCode
import org.springframework.http.HttpStatus

class BaseException (
    error: ErrorCode, value: Any? = null
) : RuntimeException() {

    private val code: HttpStatus;
    private val reason: String;

    init {
        this.code = error.code
        this.reason = if (value != null) ("${error.reason} ${error.detail.replace("{VALUE}", value.toString())}") else error.reason
    }

}