package com.core.base.corebase.domain.review.code

enum class StateType {
    TEMP,
    PROCESS,
    PAUSE,
    DELETED;

    fun isInActive() = mutableListOf(PAUSE, DELETED).contains(this)
}