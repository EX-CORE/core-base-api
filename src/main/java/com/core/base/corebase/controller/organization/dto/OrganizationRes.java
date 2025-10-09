// Copyright 2025 Kakao Healthcare Corp. All Rights Reserved.

package com.core.base.corebase.controller.organization.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationRes {
    private Long id;
    private String name;
    private String logoFileName;
    private String ceo;
    private String telNumber;
    private String address;
    private List<TeamRes> teams;
}
