// Copyright 2025 Kakao Healthcare Corp. All Rights Reserved.

package com.core.base.corebase.controller.organization.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamOrderUpdateReq {
    private Long id;
    private Integer orderNum;
    private Long parentsId;
}
