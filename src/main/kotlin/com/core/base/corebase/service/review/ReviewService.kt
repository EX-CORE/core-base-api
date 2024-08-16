package com.core.base.corebase.service.review

import com.core.base.corebase.common.code.ErrorCode
import com.core.base.corebase.common.exception.BaseException
import com.core.base.corebase.config.AuthenticationFacade
import com.core.base.corebase.controller.review.dto.*
import com.core.base.corebase.domain.review.*
import com.core.base.corebase.domain.review.code.StateType
import com.core.base.corebase.repository.*
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ReviewService(
    private val reviewBaseRepository: ReviewBaseRepository,
    private val memberRepository: MemberRepository,
    private val reviewMemberRepository: ReviewMemberRepository,
    private val organizationRepository: OrganizationRepository,
    private val authenticationFacade: AuthenticationFacade,
    private val userRepository: UserRepository
) {
    fun save(req: ReviewReq) {
        val section = req.sections
            .map {
                ReviewSection(
                    it.name,
                    it.questions.map { q ->
                        ReviewQuestion(
                            UUID.randomUUID(),
                            q.question, q.type,
                            q.limit, q.order,
                            q.choices.map { c -> ReviewChoice(UUID.randomUUID(), c.label, c.order, c.score) },
                            q.useScore,
                            q.useMultiSelect
                        )
                    },
                    it.order
                )
            };

        val reviewBase = ReviewBase(
            UUID.randomUUID(),
            req.title,
            req.description,
            req.surveyPeriod,
            req.reviewPeriod,
            req.organizationId,
            section,
            req.secretKey,
            StateType.TEMP,
            req.defaultScoreChoices
        )

        reviewBaseRepository.save(reviewBase)
            .let {
                req.memberIds.forEach { memberId ->
                    memberRepository.findByIdOrNull(memberId)
                        ?.let { member -> reviewMemberRepository.save(ReviewMember(UUID.randomUUID(), member, it.id, null)) }
                        ?: throw BaseException(ErrorCode.USER_NOT_FOUND, memberId)

                }
            }
    }

    fun get(id: UUID): ReviewRes =
        reviewBaseRepository.findByIdOrNull(id)
            ?.toRes( reviewMemberRepository.findByReviewId(id) )
            ?: throw BaseException(ErrorCode.REVIEW_NOT_FOUND, id)

    @Transactional
    fun pause(id: UUID) : Unit =
            reviewBaseRepository.findByIdOrNull(id)
                ?.pause()
                ?: throw BaseException(ErrorCode.REVIEW_NOT_FOUND, id)

    private fun ReviewBase.toRes(reviewMembers: List<ReviewMember>) =
        ReviewRes(
            id,
            title,
            description,
            surveyPeriod,
            reviewPeriod,
            organizationId,
            sections.map { it.toRes() },
            state,
            reviewMembers.map { it.toRes() },
        )

    private fun ReviewSection.toRes() =
        ReviewerSectionRes(name, questions.map { it.toRes() }, order)

    private fun ReviewQuestion.toRes() =
        QuestionRes(id, question, type, choices?.map { it.toRes() }, limit, order, useScore, useMultiSelect)

    private fun ReviewChoice.toRes() =
        ChoiceRes(id, label, order, score)

    private fun ReviewMember.toRes() =
        ReviewMemberRes(member.id, member.name)
}
