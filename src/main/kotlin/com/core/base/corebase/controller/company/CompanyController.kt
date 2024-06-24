package com.core.base.corebase.controller.company

import com.core.base.corebase.controller.company.dto.CompanyReq
import com.core.base.corebase.controller.company.dto.CompanyRes
import com.core.base.corebase.controller.company.dto.TeamRes
import com.core.base.corebase.controller.review.dto.*
import com.core.base.corebase.service.company.OrganizationService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import java.util.*


@Tag(name = "조직 API")
@RestController
@RequestMapping("/company")
class CompanyController(
    val organizationService: OrganizationService
) {

    @PostMapping
    fun save(@RequestBody req: CompanyReq): CompanyRes =
        organizationService.save(req)

    @GetMapping("/{id}")
    fun get(@PathVariable id: UUID): CompanyRes =
        organizationService.get(id)

    @GetMapping("/{id}/teams")
    fun listTeam(@PathVariable id: UUID): List<TeamRes> =
        organizationService.listTeam(id)

}