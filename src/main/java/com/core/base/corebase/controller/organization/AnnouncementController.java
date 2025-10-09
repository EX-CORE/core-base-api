// Copyright 2025 Kakao Healthcare Corp. All Rights Reserved.

package com.core.base.corebase.controller.organization;

import com.core.base.corebase.config.AuthenticationFacade;
import com.core.base.corebase.controller.organization.dto.AnnouncementReq;
import com.core.base.corebase.controller.organization.dto.AnnouncementRes;
import com.core.base.corebase.service.organization.AnnouncementService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "공지사항 API")
@RestController
@RequestMapping("/announcement")
@RequiredArgsConstructor
public class AnnouncementController {
    
    private final AnnouncementService announcementService;
    private final AuthenticationFacade authenticationFacade;
    
    @GetMapping
    public List<AnnouncementRes> getList(@RequestParam Long organizationId) {
        return announcementService.getList(authenticationFacade.getUid(), organizationId);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody AnnouncementReq announcementReq, @RequestParam Long organizationId) {
        announcementService.save(announcementReq, authenticationFacade.getUid(), organizationId);
    }
    
    @PutMapping("/{announcementId}")
    public void update(@RequestBody AnnouncementReq announcementReq, 
                      @RequestParam Long organizationId, 
                      @PathVariable Long announcementId) {
        announcementService.update(announcementReq, authenticationFacade.getUid(), organizationId, announcementId);
    }
    
    @DeleteMapping("/{announcementId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestParam Long organizationId, @PathVariable Long announcementId) {
        announcementService.delete(authenticationFacade.getUid(), organizationId, announcementId);
    }
}
