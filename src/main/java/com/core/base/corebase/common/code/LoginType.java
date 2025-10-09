package com.core.base.corebase.common.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoginType {
    REVIEWER("reviewer"),
    MANAGER("manager");

    private final String url;
}
