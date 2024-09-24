package com.core.base.corebase.controller.organization

import com.core.base.corebase.controller.organization.dto.*
import com.core.base.corebase.service.organization.OrganizationService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.util.*


@Tag(name = "조직 API")
@RestController
@RequestMapping("/organization")
class OrganizationController(
    private val organizationService: OrganizationService
) {

    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun save(@io.swagger.v3.oas.annotations.parameters.RequestBody req: OrganizationReq): OrganizationRes =
        organizationService.save(req)

    @PutMapping(path = ["/{id}"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun update(@PathVariable id: UUID, @io.swagger.v3.oas.annotations.parameters.RequestBody req: OrganizationReq): OrganizationRes =
        organizationService.update(id, req)

    @GetMapping("/{id}")
    fun get(@PathVariable id: UUID): OrganizationRes =
        organizationService.get(id)

    @GetMapping("/{id}/teams")
    fun listTeam(@PathVariable id: UUID): List<TeamRes> =
        organizationService.listTeam(id)

    @PostMapping("/{id}/teams")
    fun saveTeam(@PathVariable id: UUID, @RequestBody req: TeamReq): TeamRes =
        organizationService.saveTeam(id, req)

    @PutMapping("/{id}/teams")
    fun updateTeam(@PathVariable id: UUID, @RequestBody req: List<TeamUpdateReq>) =
        organizationService.updateTeam(id, req)

    @DeleteMapping("/{id}/teams/{teamId}")
    fun deleteTeam(@PathVariable id: UUID, @PathVariable teamId: UUID) =
        organizationService.deleteTeam(id, teamId)

}

