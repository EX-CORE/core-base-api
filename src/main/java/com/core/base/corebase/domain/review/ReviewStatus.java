package com.core.base.corebase.domain.review;

public enum ReviewStatus {
    SCHEDULED("예정"),
    IN_PROGRESS("진행중"),
    COMPLETED("종료");

    private final String displayName;

    ReviewStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
