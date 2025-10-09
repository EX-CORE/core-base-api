// Copyright 2025 Kakao Healthcare Corp. All Rights Reserved.

package com.core.base.corebase.service.organization;

import com.core.base.corebase.common.code.ErrorCode;
import com.core.base.corebase.common.exception.BaseException;
import com.core.base.corebase.controller.organization.dto.AnnouncementReq;
import com.core.base.corebase.controller.organization.dto.AnnouncementRes;
import com.core.base.corebase.domain.organization.Announcement;
import com.core.base.corebase.domain.user.code.MemberState;
import com.core.base.corebase.domain.user.code.PermissionType;
import com.core.base.corebase.repository.AnnouncementRepository;
import com.core.base.corebase.repository.MemberRepository;
import com.core.base.corebase.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnnouncementService {
    
    private final MemberRepository memberRepository;
    private final OrganizationRepository organizationRepository;
    private final AnnouncementRepository announcementRepository;
    
    @Transactional(readOnly = true)
    public List<AnnouncementRes> getList(UUID uid, Long organizationId) {
        memberRepository.findByUser_UidAndOrganizationIdAndState(uid, organizationId, MemberState.JOIN)
            .orElseThrow(() -> new BaseException(ErrorCode.ANNOUNCEMENT_NOT_ALLOWED));
        
        return announcementRepository.findByOrganizationIdOrderByCreatedAtDesc(organizationId)
            .stream()
            .map(this::toRes)
            .collect(Collectors.toList());
    }
    
    private AnnouncementRes toRes(Announcement announcement) {
        return new AnnouncementRes(
            announcement.getId(),
            announcement.getTitle(),
            announcement.getContent(),
            announcement.getCreatedAt()
        );
    }
    
    @Transactional
    public void save(AnnouncementReq announcementReq, UUID uid, Long organizationId) {
        var organization = organizationRepository.findById(organizationId)
            .orElseThrow(() -> new BaseException(ErrorCode.ORGANIZATION_NOT_FOUND, organizationId));
        
        checkAuthorization(uid, organizationId);
        
        announcementRepository.save(
            new Announcement(organization, announcementReq.getTitle(), announcementReq.getContent())
        );
    }
    
    @Transactional
    public void update(AnnouncementReq announcementReq, UUID uid, Long organizationId, Long announcementId) {
        checkAuthorization(uid, organizationId);
        
        var announcement = announcementRepository.findById(announcementId)
            .orElseThrow(() -> new BaseException(ErrorCode.ANNOUNCEMENT_NOT_FOUND, announcementId));
        
        if (!announcement.getOrganization().getId().equals(organizationId)) {
            throw new BaseException(ErrorCode.ANNOUNCEMENT_NOT_FOUND, announcementId);
        }
        
        announcement.update(announcementReq.getTitle(), announcementReq.getContent());
        announcementRepository.save(announcement);
    }
    
    @Transactional
    public void delete(UUID uid, Long organizationId, Long announcementId) {
        checkAuthorization(uid, organizationId);
        
        var announcement = announcementRepository.findById(announcementId)
            .orElseThrow(() -> new BaseException(ErrorCode.ANNOUNCEMENT_NOT_FOUND, announcementId));
        
        announcementRepository.delete(announcement);
    }
    
    private void checkAuthorization(UUID uid, Long organizationId) {
        var member = memberRepository.findByUser_UidAndOrganizationIdAndState(uid, organizationId, MemberState.JOIN)
            .orElseThrow(() -> new BaseException(ErrorCode.ANNOUNCEMENT_NOT_ALLOWED));
        
        if (!member.getPermission().equals(PermissionType.MANAGER)) {
            throw new BaseException(ErrorCode.ANNOUNCEMENT_NOT_ALLOWED);
        }
    }
}
