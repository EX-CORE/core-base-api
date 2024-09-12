package com.core.base.corebase.controller.organization

import com.core.base.corebase.config.AuthenticationFacade
import com.core.base.corebase.controller.organization.dto.AnnouncementReq
import com.core.base.corebase.service.organization.AnnouncementService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import java.util.*

@Tag(name = "공지사항 API")
@RestController
@RequestMapping("/announcement")
class AnnouncementController(
    private val announcementService: AnnouncementService,
    private val authenticationFacade: AuthenticationFacade
) {

    @GetMapping
    fun getList(@RequestParam organizationId: UUID) = announcementService.getList(authenticationFacade.uid, organizationId)

    @PostMapping
    fun save(@RequestBody announcementReq: AnnouncementReq, @RequestParam organizationId: UUID) {
        announcementService.save(announcementReq, authenticationFacade.uid, organizationId)
    }

    @PutMapping("/{announcementId}")
    fun update(@RequestBody announcementReq: AnnouncementReq, @RequestParam organizationId: UUID, @PathVariable announcementId: UUID) {
        announcementService.update(announcementReq, authenticationFacade.uid, organizationId, announcementId)
    }

}