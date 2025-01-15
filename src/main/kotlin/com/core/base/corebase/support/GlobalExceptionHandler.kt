package com.core.base.corebase.support

import com.core.base.corebase.common.code.ErrorCode
import com.core.base.corebase.common.dto.BaseExceptionRes
import com.core.base.corebase.common.exception.BaseException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<BaseExceptionRes> {
        val errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        e.printStackTrace()
        return ResponseEntity(BaseExceptionRes(errorCode, e.message), errorCode.code)
    }

    @ExceptionHandler(BaseException::class)
    fun handleBaseException(e: BaseException): ResponseEntity<BaseExceptionRes> {
        e.printStackTrace()
        return ResponseEntity(BaseExceptionRes(e.errorCode, e.reason), e.errorCode.code)
    }

}