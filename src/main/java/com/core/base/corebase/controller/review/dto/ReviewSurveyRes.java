// Copyright 2025 Kakao Healthcare Corp. All Rights Reserved.

package com.core.base.corebase.controller.review.dto;

import com.core.base.corebase.domain.review.ReviewPeriod;
import com.core.base.corebase.domain.review.code.StateType;
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
public class ReviewSurveyRes {
    private UUID id;
    private String title;
    private String description;
    private ReviewPeriod surveyPeriod;
    private Long organizationId;
    private StateType state;
    private List<String> projects;
    private UUID extraReviewer;
}
