package com.core.base.corebase.service.review

import com.core.base.corebase.common.code.ErrorCode
import com.core.base.corebase.common.exception.BaseException
import com.core.base.corebase.config.AuthenticationFacade
import com.core.base.corebase.controller.review.dto.ReviewSurveyReq
import com.core.base.corebase.controller.review.dto.ReviewSurveyRes
import com.core.base.corebase.domain.review.ReviewBase
import com.core.base.corebase.domain.review.ReviewPreSurvey
import com.core.base.corebase.repository.ReviewBaseRepository
import com.core.base.corebase.repository.ReviewMemberRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class SurveyService(
    val reviewBaseRepository: ReviewBaseRepository,
    val reviewMemberRepository: ReviewMemberRepository,
    val authenticationFacade: AuthenticationFacade
) {
    fun get(id: UUID): ReviewSurveyRes =
        reviewBaseRepository.findById(id)
            ?.toRes(reviewMemberRepository.findByReviewIdAndMemberId(id, authenticationFacade.uid)?.preSurvey)
            ?: throw BaseException(ErrorCode.REVIEW_NOT_FOUND, id)

    fun save(id: UUID, req: ReviewSurveyReq) =
        reviewMemberRepository.findByReviewIdAndMemberId(id, authenticationFacade.uid)
            ?.let {
                it.executePreSurvey(ReviewPreSurvey(req.projects, req.extraReviewMember))
            }
            ?: throw BaseException(ErrorCode.REVIEW_NOT_FOUND, id)


    private fun ReviewBase.toRes(reviewPreSurvey: ReviewPreSurvey?) =
        ReviewSurveyRes(
            id,
            title,
            description,
            surveyPeriod,
            organizationId,
            state,
            reviewPreSurvey?.projects?: emptyList(),
            reviewPreSurvey?.extraReviewMember?: null
        )
}
