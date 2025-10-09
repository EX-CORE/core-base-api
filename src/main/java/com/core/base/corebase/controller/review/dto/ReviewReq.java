// Copyright 2025 Kakao Healthcare Corp. All Rights Reserved.

package com.core.base.corebase.controller.review.dto;

import com.core.base.corebase.domain.review.ReviewChoice;
import com.core.base.corebase.domain.review.ReviewPeriod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewReq {
    private String title;
    private String description;
    private ReviewPeriod surveyPeriod;
    private ReviewPeriod reviewPeriod;
    private Long organizationId;
    private List<ReviewerSectionReq> sections;
    private List<UUID> memberIds;
    private String secretKey;
    private List<UUID> projectIds;
    private List<ReviewChoice> defaultScoreChoices;
}
