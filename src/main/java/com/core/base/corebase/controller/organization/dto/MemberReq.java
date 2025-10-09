// Copyright 2025 Kakao Healthcare Corp. All Rights Reserved.

package com.core.base.corebase.controller.organization.dto;

import com.core.base.corebase.domain.user.code.PermissionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberReq {
    private String name;
    private String email;
    private Long teamId;
    private PermissionType permission;
}
