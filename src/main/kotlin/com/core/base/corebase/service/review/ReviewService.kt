package com.core.base.corebase.service.review

import com.core.base.corebase.common.code.ErrorCode
import com.core.base.corebase.common.exception.BaseException
import com.core.base.corebase.config.AuthenticationFacade
import com.core.base.corebase.controller.review.dto.*
import com.core.base.corebase.domain.review.*
import com.core.base.corebase.domain.review.code.StateType
import com.core.base.corebase.repository.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ReviewService(
    val reviewBaseRepository: ReviewBaseRepository,
    val memberRepository: MemberRepository,
    val reviewMemberRepository: ReviewMemberRepository,
    val organizationRepository: OrganizationRepository,
    val authenticationFacade: AuthenticationFacade,
    val userRepository: UserRepository
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
                    memberRepository.findById(memberId)
                        ?.let { member -> reviewMemberRepository.save(ReviewMember(UUID.randomUUID(), member, it.id, null)) }
                        ?: throw BaseException(ErrorCode.USER_NOT_FOUND, memberId)

                }
            }
    }

    fun get(id: UUID): ReviewRes =
        reviewBaseRepository.findById(id)
            ?.toRes( reviewMemberRepository.findByReviewId(id) )
            ?: throw BaseException(ErrorCode.REVIEW_NOT_FOUND, id)

//    fun getReview(id: UUID, memberId: UUID): ReviewDetailRes =
//        reviewBaseRepository.findById(id)
//            ?.let {
//                val reviewerId = UUID.randomUUID()
//                reviewMemberRepository.findByReviewIdAndRevieweeIdAndReviewerId(id, revieweeId, reviewerId)
//                    ?: throw BaseException(ErrorCode.REVIEWER_NOT_ALLOWED, id)
//                it.toDetailRes(revieweeId)
//            }
//            ?: throw BaseException(ErrorCode.REVIEW_NOT_FOUND, id)
//
//
//    fun listReviewByReviewee(id: UUID): List<ReviewerRes> =
//        reviewMemberRepository.findByReviewerId(id).map { it.toRes() }
//
//    fun listReviewByReviewer(id: UUID): List<ReviewerRes> =
//        reviewMemberRepository.findByRevieweeId(id)
//            .stream()
//            .collect(groupingBy(ReviewMember::reviewId))
//            .entries.stream()
//            .map { it ->
//                reviewBaseRepository.findById(it.key)
//                    ?.let { ReviewerRes(id, "", it.title, it.description, it.surveyPeriod, it.reviewPeriod, it.state) }
//                    ?: throw BaseException(ErrorCode.REVIEW_NOT_FOUND, id)
//            }
//            .toList()

    @Transactional
    fun pause(id: UUID) : Unit =
            reviewBaseRepository.findById(id)
                ?.let { it.pause() }
                ?: throw BaseException(ErrorCode.REVIEW_NOT_FOUND, id)


//    private fun ReviewMember.toRes(): ReviewerRes {
//        val reviewee = userRepository.findByUid(revieweeId)!!
//        return reviewBaseRepository.findById(reviewId)
//            ?.let { ReviewerRes(id, reviewee.name, it.title, it.description,  it.surveyPeriod, it.reviewPeriod, it.state) }
//            ?: throw BaseException(ErrorCode.REVIEW_NOT_FOUND, id)
//    }
//
//
//    private fun ReviewBase.toDetailRes(revieweeId: UUID): ReviewDetailRes {
//        val reviewee = userRepository.findByUid(revieweeId)
//            ?: throw BaseException(ErrorCode.USER_NOT_FOUND, revieweeId)
//
//        return ReviewDetailRes(
//            id,
//            title,
//            description,
//            surveyPeriod,
//            reviewPeriod,
//            organizationId,
//            sections.map { it.toRes() },
//            state,
//            reviewee.name
//        )
//    }
//
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
