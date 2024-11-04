package com.core.base.corebase.domain.review

import com.core.base.corebase.domain.common.StringListConverter
import com.core.base.corebase.domain.user.Member
import jakarta.persistence.*

@Entity(name = "review_pre_survey")
class ReviewPreSurvey(
    projects: List<String>,
    extraReviewMember : Member,
    reviewMember: ReviewMember
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L

    @Convert(converter = StringListConverter::class)
    var projects: List<String> = projects; protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "extra_review_member_id", nullable = true)
    var extraReviewMember = extraReviewMember; protected set

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_member_id", nullable = false)
    var reviewMember: ReviewMember = reviewMember; protected set



}