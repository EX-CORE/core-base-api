package com.core.base.corebase.service.review

import com.core.base.corebase.common.exception.BaseException
import com.core.base.corebase.common.code.ErrorCode
import com.core.base.corebase.config.AuthenticationFacade
import com.core.base.corebase.controller.company.dto.ProjectRes
import com.core.base.corebase.controller.review.dto.*
import com.core.base.corebase.domain.review.*
import com.core.base.corebase.repository.*
import org.springframework.stereotype.Service
import java.util.*

@Service
class SurveyService(
    val reviewRepository: ReviewRepository,
    val userRepository: UserRepository,
    val companyRepository: CompanyRepository,
    val reviewSurveyRepository: ReviewSurveyRepository,
    val authenticationFacade: AuthenticationFacade
) {
    fun get(id: UUID): ReviewSurveyRes =
        reviewRepository.findById(id)
            .map {
                it.toRes(reviewSurveyRepository.findByReviewIdAndReviewerId(id, authenticationFacade.uid))
            }.orElseThrow { throw BaseException(ErrorCode.REVIEW_NOT_FOUND, id) }

    fun save(id: UUID, req: ReviewSurveyReq) =
        reviewRepository.findById(id)
            .map {
                reviewSurveyRepository.save(ReviewSurvey(id, authenticationFacade.uid, req.projectIds, req.extraReviewer))
            }.orElseThrow { throw BaseException(ErrorCode.REVIEW_NOT_FOUND, id) }


    private fun Review.toRes(reviewSurvey: Optional<ReviewSurvey>) =
        ReviewSurveyRes(
            id,
            title,
            description,
            surveyPeriod,
            companyId,
            state,
            reviewSurvey.map { getProjects(companyId, projectIds) }.orElse(null),
            reviewSurvey.map { it.extraReviewer }.orElse(null)
        )

    private fun getProjects(companyId: UUID, ids: List<UUID>) =
        companyRepository.findById(companyId)
            .orElseThrow{ throw BaseException(ErrorCode.COMPANY_NOT_FOUND, companyId) }
            .let {
                it.projects.stream()
                    .filter { project -> ids.contains(project.id) }
                    .toList()
            }.map { ProjectRes(it.id, it.name) }
}
