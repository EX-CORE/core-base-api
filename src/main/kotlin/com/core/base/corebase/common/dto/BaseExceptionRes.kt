package com.core.base.corebase.common.dto

import com.core.base.corebase.common.code.ErrorCode
import java.time.ZonedDateTime

class BaseExceptionRes(
    val code: ErrorCode,
    val reason : String?
) {
    val status = code.code
    val timeStamp = ZonedDateTime.now().toString()
}