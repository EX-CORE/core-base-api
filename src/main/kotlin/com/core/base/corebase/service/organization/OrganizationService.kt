package com.core.base.corebase.service.organization

import com.core.base.corebase.common.code.ErrorCode
import com.core.base.corebase.common.exception.BaseException
import com.core.base.corebase.controller.organization.dto.OrganizationReq
import com.core.base.corebase.controller.organization.dto.OrganizationRes
import com.core.base.corebase.controller.organization.dto.TeamReq
import com.core.base.corebase.controller.organization.dto.TeamRes
import com.core.base.corebase.domain.organization.Organization
import com.core.base.corebase.domain.organization.Team
import com.core.base.corebase.repository.OrganizationRepository
import org.springframework.stereotype.Service
import java.util.*


@Service
class OrganizationService(
    private var organizationRepository: OrganizationRepository
) {

    fun save(req: OrganizationReq): OrganizationRes =
        organizationRepository.save(
            Organization(
                UUID.randomUUID(),
                req.name,
                req.logo?.originalFilename,
                req.ceo,
                req.telNumber,
                req.address,
                req.teams?.map { saveTeam(it, null) }?.flatten()
            )
        ).toRes()

    fun get(id: UUID): OrganizationRes =
        getRes(id)

    fun listTeam(id: UUID): List<TeamRes> =
        getRes(id).teams.orEmpty()

    private fun saveTeam(teamReq: TeamReq, parentsId: UUID?): List<Team> {
        val teamList: MutableList<Team> = mutableListOf()
        val team = Team(UUID.randomUUID(), teamReq.name, teamReq.order, parentsId)
        teamList.add(team)
        if (!teamReq.subTeams.isNullOrEmpty())
            teamList.addAll(teamReq.subTeams.map { saveTeam(it, team.id) }.flatten())
        return teamList
    }

    private fun getRes(id: UUID) =
        organizationRepository.findById(id)
            .map { it.toRes() }
            .orElseThrow { BaseException(ErrorCode.ORGANIZATION_NOT_FOUND, id) }

    private fun Organization.toRes(): OrganizationRes =
        OrganizationRes(
            id, name, logoFileName, ceo, telNumber, address,
            teams?.map { it.toRes() }
        )

    fun Team.toRes(): TeamRes =
        TeamRes(id, name, order, parentId)
}