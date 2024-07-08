package com.core.base.corebase.controller.organization

import com.core.base.corebase.controller.organization.dto.OrganizationReq
import com.core.base.corebase.controller.organization.dto.OrganizationRes
import com.core.base.corebase.controller.organization.dto.TeamRes
import com.core.base.corebase.service.organization.OrganizationService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import java.util.*


@Tag(name = "조직 API")
@RestController
@RequestMapping("/organization")
class OrganizationController(
    val organizationService: OrganizationService
) {

    @PostMapping
    fun save(@RequestBody req: OrganizationReq): OrganizationRes =
        organizationService.save(req)

    @GetMapping("/{id}")
    fun get(@PathVariable id: UUID): OrganizationRes =
        organizationService.get(id)

    @GetMapping("/{id}/teams")
    fun listTeam(@PathVariable id: UUID): List<TeamRes> =
        organizationService.listTeam(id)

}