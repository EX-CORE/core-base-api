package com.core.base.corebase.service.company

import com.core.base.corebase.common.code.ErrorCode
import com.core.base.corebase.common.exception.BaseException
import com.core.base.corebase.controller.company.dto.*
import com.core.base.corebase.domain.company.Company
import com.core.base.corebase.domain.company.Project
import com.core.base.corebase.domain.company.Team
import com.core.base.corebase.repository.CompanyRepository
import org.springframework.stereotype.Service
import java.util.*


@Service
class CompanyService(
    var companyRepository: CompanyRepository
) {

    fun save(req: CompanyReq): CompanyRes =
        companyRepository.save(
            Company(
                UUID.randomUUID(),
                req.name,
                req.ceo,
                req.telNumber,
                req.address,
                req.projects.map { Project(UUID.randomUUID(), it) },
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
        companyRepository.findById(id)
            .map { it.toRes() }
            .orElseThrow { BaseException(ErrorCode.COMPANY_NOT_FOUND, id) }

    private fun Company.toRes(): CompanyRes =
        CompanyRes(
            id, name, ceo, telNumber, address,
            projects.map { it.toRes() },
            teams.map { it.toRes() }
        )

    private fun Project.toRes(): ProjectRes =
        ProjectRes(id, name)

    fun Team.toRes(): TeamRes =
        TeamRes(id, name, order, parentId)
}