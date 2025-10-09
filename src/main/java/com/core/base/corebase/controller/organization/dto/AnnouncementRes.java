package com.core.base.corebase.controller.organization.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementRes {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
}
