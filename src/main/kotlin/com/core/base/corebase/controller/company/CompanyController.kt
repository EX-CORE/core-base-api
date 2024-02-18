package com.core.base.corebase.controller.company

import com.core.base.corebase.controller.review.dto.*
import com.core.base.corebase.service.company.CompanyService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import java.util.*


@Tag(name = "회사 API")
@RestController
@RequestMapping("/company")
class CompanyController(
    val companyService: CompanyService
) {

    @PostMapping
    fun save(@RequestBody req: CompanyReq): CompanyRes =
        companyService.save(req)

    @GetMapping("/{id}")
    fun get(@PathVariable id: UUID): CompanyRes =
        companyService.get(id)

    @GetMapping("/{id}/teams")
    fun listTeam(@PathVariable id: UUID): List<TeamRes> =
        companyService.listTeam(id)

}