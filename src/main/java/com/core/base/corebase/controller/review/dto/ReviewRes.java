// Copyright 2025 Kakao Healthcare Corp. All Rights Reserved.

package com.core.base.corebase.controller.review.dto;

import com.core.base.corebase.domain.review.ReviewPeriod;
import com.core.base.corebase.domain.review.code.StateType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ReviewRes extends ReviewSimpleRes {
    private List<ReviewMemberRes> members;
    
    public ReviewRes(UUID id, String title, String description, 
                    ReviewPeriod surveyPeriod, ReviewPeriod reviewPeriod, 
                    Long organizationId, List<ReviewerSectionRes> sections, 
                    StateType state, List<ReviewMemberRes> members) {
        super(id, title, description, surveyPeriod, reviewPeriod, organizationId, sections, state);
        this.members = members;
    }
}
