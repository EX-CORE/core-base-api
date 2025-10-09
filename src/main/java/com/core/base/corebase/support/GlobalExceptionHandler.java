package com.core.base.corebase.support;

import com.core.base.corebase.common.code.ErrorCode;
import com.core.base.corebase.common.dto.BaseExceptionRes;
import com.core.base.corebase.common.exception.BaseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseExceptionRes> handleException(Exception e) {
        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        e.printStackTrace();
        return new ResponseEntity<>(
            new BaseExceptionRes(errorCode, e.getMessage()),
            errorCode.getCode()
        );
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseExceptionRes> handleBaseException(BaseException e) {
        e.printStackTrace();
        return new ResponseEntity<>(
            new BaseExceptionRes(e.getErrorCode(), e.getReason()),
            e.getErrorCode().getCode()
        );
    }
}
