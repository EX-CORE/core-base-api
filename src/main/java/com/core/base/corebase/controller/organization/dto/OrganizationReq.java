// Copyright 2025 Kakao Healthcare Corp. All Rights Reserved.

package com.core.base.corebase.controller.organization.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationReq {
    private String name;
    
    @Nullable
    private MultipartFile logo;
    
    @Nullable
    private String ceo;
    
    @Nullable
    private String telNumber;
    
    @Nullable
    private String address;
}
