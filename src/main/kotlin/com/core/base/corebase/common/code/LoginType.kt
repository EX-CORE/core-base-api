package com.core.base.corebase.common.code

enum class LoginType(
    val url: String
) {
    REVIEWER("/review"), MANAGER("/manager");
}