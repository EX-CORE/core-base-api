package com.core.base.corebase.common.dto;

import com.core.base.corebase.common.code.ErrorCode;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class BaseExceptionRes {
    private final ErrorCode code;
    private final String reason;
    private final int status;
    private final String timeStamp;

    public BaseExceptionRes(ErrorCode code, String reason) {
        this.code = code;
        this.reason = reason;
        this.status = code.getCode().value();
        this.timeStamp = ZonedDateTime.now().toString();
    }
}
