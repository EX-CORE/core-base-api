package com.core.base.corebase.domain.review.code

enum class StateType {
    READY,
    PROCESS,
    STOPPED,
    DONE,
    DELETED;

    fun isInActive() = mutableListOf(STOPPED, DONE, DELETED).contains(this)
}