package com.core.base.corebase.domain.review.code;

import java.util.List;

public enum StateType {
    READY,
    PROCESS,
    STOPPED,
    DONE,
    DELETED;

    public boolean isInActive() {
        return List.of(STOPPED, DONE, DELETED).contains(this);
    }
}
