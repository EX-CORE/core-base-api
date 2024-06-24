package com.core.base.corebase.service.company

import com.core.base.corebase.common.code.ErrorCode
import com.core.base.corebase.common.exception.BaseException
import com.core.base.corebase.controller.company.dto.*
import com.core.base.corebase.domain.company.Organization
import com.core.base.corebase.domain.company.Team
import com.core.base.corebase.repository.OrganizationRepository
import org.springframework.stereotype.Service
import java.util.*


@Service
class OrganizationService(
    var organizationRepository: OrganizationRepository
) {

    fun save(req: CompanyReq): CompanyRes =
        organizationRepository.save(
            Organization(
                UUID.randomUUID(),
                req.name,
                req.ceo,
                req.telNumber,
                req.address,
                req.teams.map { saveTeam(it, null) }.flatten()
            )
        ).toRes()

    fun get(id: UUID): CompanyRes =
        getRes(id)

    fun listTeam(id: UUID): List<TeamRes> =
        getRes(id).teams

    private fun saveTeam(teamReq: TeamReq, parentsId: UUID?): List<Team> {
        val teamList : MutableList<Team> = mutableListOf()
        val team = Team(UUID.randomUUID(), teamReq.name, teamReq.order, parentsId)
        teamList.add(team)
        if (!teamReq.subTeams.isNullOrEmpty())
            teamList.addAll( teamReq.subTeams.map { saveTeam(it, team.id) }.flatten())
        return teamList
    }

    private fun getRes(id: UUID) =
        organizationRepository.findById(id)
            .map { it.toRes() }
            .orElseThrow { BaseException(ErrorCode.COMPANY_NOT_FOUND, id) }

    private fun Organization.toRes(): CompanyRes =
        CompanyRes(
            id, name, ceo, telNumber, address,
            teams.map { it.toRes() }
        )

    fun Team.toRes(): TeamRes =
        TeamRes(id, name, order, parentId)
}