package com.core.base.corebase.service.organization

import com.core.base.corebase.common.code.ErrorCode
import com.core.base.corebase.common.exception.BaseException
import com.core.base.corebase.config.AuthenticationFacade
import com.core.base.corebase.controller.organization.dto.*
import com.core.base.corebase.domain.organization.Organization
import com.core.base.corebase.domain.organization.Team
import com.core.base.corebase.domain.user.Member
import com.core.base.corebase.domain.user.code.MemberState
import com.core.base.corebase.domain.user.code.PermissionType
import com.core.base.corebase.repository.MemberRepository
import com.core.base.corebase.repository.OrganizationRepository
import com.core.base.corebase.repository.TeamRepository
import com.core.base.corebase.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Transactional(readOnly = true)
@Service
class OrganizationService(
    private var organizationRepository: OrganizationRepository,
    private var memberRepository: MemberRepository,
    private var userRepository: UserRepository,
    private var authenticationFacade: AuthenticationFacade, private val teamRepository: TeamRepository
) {

    @Transactional
    fun save(req: OrganizationReq): OrganizationRes =
        organizationRepository.save(
            Organization(
                req.name,
                req.logo?.originalFilename,
                req.ceo,
                req.telNumber,
                req.address
            )
        ).apply {
            var user = userRepository.findByUid(authenticationFacade.uid)
                ?: throw BaseException(ErrorCode.USER_NOT_FOUND, authenticationFacade.uid)
            memberRepository.save(
                Member(
                    authenticationFacade.email,
                    authenticationFacade.name,
                    user, this, null,
                    PermissionType.MANAGER, MemberState.JOIN
                )
            )
        }.toRes()

    @Transactional
    fun update(id: Long, req: OrganizationReq): OrganizationRes =
        getEntity(id)
            .let {
                it.update(
                    req.name,
                    req.logo?.originalFilename,
                    req.ceo,
                    req.telNumber,
                    req.address
                )
                it.toRes()
            }

    fun get(id: Long): OrganizationRes =
        getRes(id)

    fun listTeam(id: Long): List<TeamRes> =
        getRes(id).teams.orEmpty()

    fun listMember(id: Long): List<MemberRes> =
        getEntity(id)
            .let {
                memberRepository.findByOrganization(it)
                    .map { member -> member.toRes() }
            }

    @Transactional
    fun saveMember(id: Long, req: MemberReq): MemberRes {
       getEntity(id)
           .let {
               if (memberRepository.existsByEmailAndOrganization(req.email, it)) {
                   throw BaseException(ErrorCode.MEMBER_EMAIL_DUPLICATE, req.email)
               }
               val member = Member(
                   req.email,
                   req.name,
                   userRepository.findByEmail(req.email) ?: null,
                   it,
                   req.teamId?.let { getTeamEntity(id, it) },
                   req.permission
               )
               memberRepository.save(member)
               return member.toRes()
           }
    }

    @Transactional
    fun updateTeam(id: Long, teams: List<TeamUpdateReq>) {
        teams.map {
            getTeamEntity(id, it.id)
                .update(it.name, it.orderNum, it.parentsId)
        }
    }

    @Transactional
    fun updateTeamOrder(id: Long, teams: List<TeamOrderUpdateReq>) {
        teams.map {
            getTeamEntity(id, it.id)
                .update(it.orderNum, it.parentsId)
        }
    }

    @Transactional
    fun deleteTeam(organizationId: Long, teamId: Long) =
        getTeamEntity(organizationId, teamId)
            .run { teamRepository.delete(this) }

    @Transactional
    fun saveTeam(id: Long, req: TeamReq): TeamRes {
        val organization = getEntity(id)
        val team = Team(req.name, req.orderNum, req.parentsId, organization)
        teamRepository.save(team)
        return team.toRes()
    }

    private fun getEntity(id: Long) =
        organizationRepository.findById(id)
            .orElseThrow { BaseException(ErrorCode.ORGANIZATION_NOT_FOUND, id) }

    private fun getTeamEntity(organizationId: Long, teamId: Long) =
        getEntity(organizationId)
            .let {
                teamRepository.findByOrganizationAndId(it, teamId)
                    .orElseThrow { BaseException(ErrorCode.ORGANIZATION_HAS_NOT_TEAM, teamId) }
            }

    private fun getMemberEntity(organizationId: Long, memberId: Long) =
        getEntity(organizationId)
            .let {
                memberRepository.findByOrganizationAndId(it, memberId)
                    .orElseThrow { BaseException(ErrorCode.MEMBER_NOT_FOUND, memberId) }
            }

    private fun getRes(id: Long) =
        getEntity(id)
            .toRes()

    private fun Organization.toRes(): OrganizationRes =
        OrganizationRes(
            id, name, logoFileName, ceo, telNumber, address,
            teams?.map { it.toRes() }
        )

    fun Team.toRes(): TeamRes =
        TeamRes(id, name, orderNum, parents?.id)

    fun Member.toRes(): MemberRes =
        MemberRes(id, name, email, team?.name, permission, state)


}