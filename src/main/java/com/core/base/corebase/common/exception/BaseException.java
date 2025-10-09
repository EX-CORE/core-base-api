package com.core.base.corebase.common.exception;

import com.core.base.corebase.common.code.ErrorCode;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String reason;

    public BaseException(ErrorCode errorCode) {
        this(errorCode, null);
    }

    public BaseException(ErrorCode errorCode, Object value) {
        this.errorCode = errorCode;
        if (value != null) {
            this.reason = errorCode.getReason() + " " +
                         errorCode.getDetail().replace("{VALUE}", value.toString());
        } else {
            this.reason = errorCode.getReason();
        }
    }
}
