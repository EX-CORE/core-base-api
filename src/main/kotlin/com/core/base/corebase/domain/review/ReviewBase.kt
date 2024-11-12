package com.core.base.corebase.domain.review

import com.core.base.corebase.common.code.ErrorCode
import com.core.base.corebase.common.exception.BaseException
import com.core.base.corebase.domain.organization.Organization
import com.core.base.corebase.domain.review.code.StateType
import jakarta.persistence.*
import java.time.LocalDate

@Entity(name = "review_base")
class ReviewBase(
    title: String,
    description: String,
    surveyPeriod: SurveyPeriod,
    reviewPeriod: ReviewPeriod,
    organization: Organization,
    secretKey: String?,
    state: StateType
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L

    var title = title; protected set

    var description = description; protected set
    @Embedded
    var surveyPeriod = surveyPeriod; protected set
    @Embedded
    var reviewPeriod = reviewPeriod; protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    var organization: Organization = organization; protected set

    @OneToMany(mappedBy = "reviewBase", cascade = [CascadeType.ALL])
    protected val mutableReviewSections: MutableList<ReviewSection> = mutableListOf()
    val sections: List<ReviewSection> get() = mutableReviewSections.toList()

    @OneToMany(mappedBy = "reviewBase", cascade = [CascadeType.ALL])
    protected val mutableDefaultScoreChoices: MutableList<ReviewChoice> = mutableListOf()
    val defaultScoreChoices: List<ReviewChoice> get() = mutableDefaultScoreChoices.toList()

    var secretKey = secretKey; protected set

    @Enumerated(EnumType.STRING)
    var state = state; protected set

    fun pause() {
        if (!validPause())
            throw BaseException(ErrorCode.REVIEW_NOT_FOUND, id)
        this.state = StateType.PAUSE;
    }

    private fun validPause() : Boolean {
        return if (state.isInActive()) false
        else LocalDate.now().let {
            this.surveyPeriod.isBefore(it) || this.surveyPeriod.between(it)
        }
    }

}