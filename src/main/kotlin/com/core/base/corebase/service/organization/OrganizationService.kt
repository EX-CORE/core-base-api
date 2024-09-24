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
import org.springframework.stereotype.Service
import java.util.*


@Service
class OrganizationService(
    private var organizationRepository: OrganizationRepository,
    private var memberRepository: MemberRepository,
    private var authenticationFacade: AuthenticationFacade
) {

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
            memberRepository.save(
                Member(authenticationFacade.email,
                    authenticationFacade.name,
                    authenticationFacade.uid,
                    this.id, null,
                    PermissionType.MANAGER, MemberState.JOIN))
        }.toRes()

    fun update(id: UUID, req: OrganizationReq): OrganizationRes =
        getEntity(id).apply {
            name = req.name
            logoFileName = req.logo?.originalFilename
            ceo = req.ceo
            telNumber = req.telNumber
            address = req.address
        }.let {
            organizationRepository.save(it)
        }.toRes()

    fun get(id: UUID): OrganizationRes =
        getRes(id)

    fun listTeam(id: UUID): List<TeamRes> =
        getRes(id).teams.orEmpty()


    fun updateTeam(id: UUID, teams: List<TeamUpdateReq>) {
        val organization = getEntity(id)
        teams.map {
            organization.updateTeamById(it.id, it.name, it.order, it.parentsId)
        }
        organizationRepository.save(organization)
    }

    fun deleteTeam(id: UUID, teamId: UUID) =
        getEntity(id)
            .let {
                it.removeTeamById(teamId)
                organizationRepository.save(it)
            }

    fun saveTeam(id: UUID, req: TeamReq): TeamRes {
        val organization = getEntity(id)
        val team = Team(req.name, req.order, req.parentsId)
        organization.addTeam(team)
        organizationRepository.save(organization)
        return team.toRes()
    }


    private fun getEntity(id: UUID) =
        organizationRepository.findById(id)
            .orElseThrow { BaseException(ErrorCode.ORGANIZATION_NOT_FOUND, id) }

    private fun getRes(id: UUID) =
        getEntity(id)
            .toRes()

    private fun Organization.toRes(): OrganizationRes =
        OrganizationRes(
            id, name, logoFileName, ceo, telNumber, address,
            teams?.map { it.toRes() }
        )

    fun Team.toRes(): TeamRes =
        TeamRes(id, name, order, parentId)


}