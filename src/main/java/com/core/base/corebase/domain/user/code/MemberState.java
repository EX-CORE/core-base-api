package com.core.base.corebase.domain.user.code;

public enum MemberState {
    /**
     * Requested to join
     */
    WAIT,

    /**
     * Active member
     */
    JOIN,

    /**
     * Left the organization
     */
    QUIT,

    /**
     * Request denied
     */
    DENY
}
