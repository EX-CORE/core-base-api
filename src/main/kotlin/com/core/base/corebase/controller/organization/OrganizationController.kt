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
    fun update(@PathVariable id: Long, @io.swagger.v3.oas.annotations.parameters.RequestBody req: OrganizationReq): OrganizationRes =
        organizationService.update(id, req)

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): OrganizationRes =
        organizationService.get(id)

    @GetMapping("/{id}/teams")
    fun listTeam(@PathVariable id: Long): List<TeamRes> =
        organizationService.listTeam(id)

    @PostMapping("/{id}/teams")
    fun saveTeam(@PathVariable id: Long, @RequestBody req: TeamReq): TeamRes =
        organizationService.saveTeam(id, req)

    @PutMapping("/{id}/teams")
    fun updateTeam(@PathVariable id: Long, @RequestBody req: List<TeamUpdateReq>) =
        organizationService.updateTeam(id, req)

    @PatchMapping("/{id}/teams/order")
    fun updateTeamOrder(@PathVariable id: Long, @RequestBody req: List<TeamOrderUpdateReq>) =
        organizationService.updateTeamOrder(id, req)

    @DeleteMapping("/{id}/teams/{teamId}")
    fun deleteTeam(@PathVariable id: Long, @PathVariable teamId: Long) =
        organizationService.deleteTeam(id, teamId)

    @GetMapping("/{id}/members")
    fun listMember(@PathVariable id: Long): List<MemberRes> =
        organizationService.listMember(id)

    @PostMapping("/{id}/members")
    fun saveMember(@PathVariable id: Long, @RequestBody req: MemberReq): MemberRes =
        organizationService.saveMember(id, req)
}

