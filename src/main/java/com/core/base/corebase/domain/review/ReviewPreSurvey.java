// Copyright 2025 Kakao Healthcare Corp. All Rights Reserved.

package com.core.base.corebase.domain.review;

import com.core.base.corebase.domain.common.StringListConverter;
import com.core.base.corebase.domain.user.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "review_pre_survey")
public class ReviewPreSurvey {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Convert(converter = StringListConverter.class)
    @Column(columnDefinition = "TEXT")
    private List<String> projects;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "extra_review_member_id")
    private Member extraReviewMember;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_member_id", nullable = false)
    private ReviewMember reviewMember;
    
    protected ReviewPreSurvey() {
        // For JPA
    }
    
    public ReviewPreSurvey(List<String> projects, Member extraReviewMember, ReviewMember reviewMember) {
        this.projects = projects;
        this.extraReviewMember = extraReviewMember;
        this.reviewMember = reviewMember;
    }
}
