package com.core.base.corebase.config;

import com.core.base.corebase.common.code.ErrorCode;
import com.core.base.corebase.common.exception.BaseException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.UUID;

@Component
@RequestScope
public class AuthenticationFacade {

    private UUID uid;
    private String email;
    private String name;
    private boolean initialized = false;

    public UUID getUid() {
        if (!initialized) throw new BaseException(ErrorCode.INVALID_TOKEN);
        return uid;
    }

    public String getEmail() {
        if (!initialized) throw new BaseException(ErrorCode.INVALID_TOKEN);
        return email;
    }

    public String getName() {
        if (!initialized) throw new BaseException(ErrorCode.INVALID_TOKEN);
        return name;
    }

    public void setInfo(UUID uid, String email, String name) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.initialized = true;
    }
}
