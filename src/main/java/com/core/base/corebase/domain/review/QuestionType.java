package com.core.base.corebase.domain.review;

public enum QuestionType {
    MULTIPLE_CHOICE("객관식"),
    SHORT_ANSWER("주관식"),
    RATING("등급형");

    private final String displayName;

    QuestionType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
