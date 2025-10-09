// Copyright 2025 Kakao Healthcare Corp. All Rights Reserved.

package com.core.base.corebase.controller.organization;

import com.core.base.corebase.controller.organization.dto.*;
import com.core.base.corebase.service.organization.OrganizationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "조직 API")
@RestController
@RequestMapping("/organization")
@RequiredArgsConstructor
public class OrganizationController {
    
    private final OrganizationService organizationService;
    
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public OrganizationRes save(@io.swagger.v3.oas.annotations.parameters.RequestBody OrganizationReq req) {
        return organizationService.save(req);
    }
    
    @PutMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public OrganizationRes update(@PathVariable Long id, 
                                  @io.swagger.v3.oas.annotations.parameters.RequestBody OrganizationReq req) {
        return organizationService.update(id, req);
    }
    
    @GetMapping("/{id}")
    public OrganizationRes get(@PathVariable Long id) {
        return organizationService.get(id);
    }
    
    @GetMapping("/{id}/teams")
    public List<TeamRes> listTeam(@PathVariable Long id) {
        return organizationService.listTeam(id);
    }
    
    @PostMapping("/{id}/teams")
    public TeamRes saveTeam(@PathVariable Long id, @RequestBody TeamReq req) {
        return organizationService.saveTeam(id, req);
    }
    
    @PutMapping("/{id}/teams")
    public void updateTeam(@PathVariable Long id, @RequestBody List<TeamUpdateReq> req) {
        organizationService.updateTeam(id, req);
    }
    
    @PatchMapping("/{id}/teams/order")
    public void updateTeamOrder(@PathVariable Long id, @RequestBody List<TeamOrderUpdateReq> req) {
        organizationService.updateTeamOrder(id, req);
    }
    
    @DeleteMapping("/{id}/teams/{teamId}")
    public void deleteTeam(@PathVariable Long id, @PathVariable Long teamId) {
        organizationService.deleteTeam(id, teamId);
    }
    
    @GetMapping("/{id}/members")
    public List<MemberRes> listMember(@PathVariable Long id) {
        return organizationService.listMember(id);
    }
    
    @PostMapping("/{id}/members")
    public MemberRes saveMember(@PathVariable Long id, @RequestBody MemberReq req) {
        return organizationService.saveMember(id, req);
    }
}
