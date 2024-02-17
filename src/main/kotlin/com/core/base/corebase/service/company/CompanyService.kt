package com.core.base.corebase.service.company

import com.core.base.corebase.common.exception.BaseException
import com.core.base.corebase.common.exception.code.ErrorCode
import com.core.base.corebase.controller.review.dto.CompanyRes
import com.core.base.corebase.controller.review.dto.ProjectRes
import com.core.base.corebase.controller.review.dto.TeamRes
import com.core.base.corebase.domain.company.Company
import com.core.base.corebase.domain.company.Project
import com.core.base.corebase.domain.company.Team
import com.core.base.corebase.repository.CompanyRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import java.util.*


@Service
@RequiredArgsConstructor
class CompanyService(
    var companyRepository: CompanyRepository
) {

    fun get(id: UUID): CompanyRes =
        getRes(id)

    fun listTeam(id: UUID): List<TeamRes> =
        getRes(id).teams

    private fun getRes(id: UUID) =
        companyRepository.findById(id)
            .map { it -> it.toRes() }
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
        TeamRes(id, name, parentId)
}