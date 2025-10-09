// Copyright 2025 Kakao Healthcare Corp. All Rights Reserved.

package com.core.base.corebase.service.organization;

import com.core.base.corebase.common.code.ErrorCode;
import com.core.base.corebase.common.exception.BaseException;
import com.core.base.corebase.config.AuthenticationFacade;
import com.core.base.corebase.controller.organization.dto.*;
import com.core.base.corebase.domain.organization.Organization;
import com.core.base.corebase.domain.organization.Team;
import com.core.base.corebase.domain.user.Member;
import com.core.base.corebase.domain.user.code.MemberState;
import com.core.base.corebase.domain.user.code.PermissionType;
import com.core.base.corebase.repository.MemberRepository;
import com.core.base.corebase.repository.OrganizationRepository;
import com.core.base.corebase.repository.TeamRepository;
import com.core.base.corebase.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrganizationService {
    
    private final OrganizationRepository organizationRepository;
    private final MemberRepository memberRepository;
    private final UserRepository userRepository;
    private final AuthenticationFacade authenticationFacade;
    private final TeamRepository teamRepository;
    
    @Transactional
    public OrganizationRes save(OrganizationReq req) {
        var organization = organizationRepository.save(
            new Organization(
                req.getName(),
                req.getLogo() != null ? req.getLogo().getOriginalFilename() : null,
                req.getCeo(),
                req.getTelNumber(),
                req.getAddress()
            )
        );
        
        var user = userRepository.findByUid(authenticationFacade.getUid())
            .orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND, authenticationFacade.getUid()));
        
        memberRepository.save(
            new Member(
                authenticationFacade.getEmail(),
                authenticationFacade.getName(),
                user,
                organization,
                null,
                PermissionType.MANAGER,
                MemberState.JOIN
            )
        );
        
        return toRes(organization);
    }
    
    @Transactional
    public OrganizationRes update(Long id, OrganizationReq req) {
        var organization = getEntity(id);
        organization.update(
            req.getName(),
            req.getLogo() != null ? req.getLogo().getOriginalFilename() : null,
            req.getCeo(),
            req.getTelNumber(),
            req.getAddress()
        );
        return toRes(organization);
    }
    
    public OrganizationRes get(Long id) {
        return getRes(id);
    }
    
    public List<TeamRes> listTeam(Long id) {
        var res = getRes(id);
        return res.getTeams() != null ? res.getTeams() : List.of();
    }
    
    public List<MemberRes> listMember(Long id) {
        var organization = getEntity(id);
        return memberRepository.findByOrganization(organization)
            .stream()
            .map(this::toMemberRes)
            .collect(Collectors.toList());
    }
    
    @Transactional
    public MemberRes saveMember(Long id, MemberReq req) {
        var organization = getEntity(id);
        
        if (memberRepository.existsByEmailAndOrganization(req.getEmail(), organization)) {
            throw new BaseException(ErrorCode.MEMBER_EMAIL_DUPLICATE, req.getEmail());
        }
        
        var user = userRepository.findByEmail(req.getEmail()).orElse(null);
        var team = req.getTeamId() != null ? getTeamEntity(id, req.getTeamId()) : null;
        
        var member = new Member(
            req.getEmail(),
            req.getName(),
            user,
            organization,
            team,
            req.getPermission(),
            null
        );
        
        memberRepository.save(member);
        return toMemberRes(member);
    }
    
    @Transactional
    public void updateTeam(Long id, List<TeamUpdateReq> teams) {
        teams.forEach(teamReq -> {
            var team = getTeamEntity(id, teamReq.getId());
            team.update(teamReq.getName(), teamReq.getOrderNum(), teamReq.getParentsId());
        });
    }
    
    @Transactional
    public void updateTeamOrder(Long id, List<TeamOrderUpdateReq> teams) {
        teams.forEach(teamReq -> {
            var team = getTeamEntity(id, teamReq.getId());
            team.update(teamReq.getOrderNum(), teamReq.getParentsId());
        });
    }
    
    @Transactional
    public void deleteTeam(Long organizationId, Long teamId) {
        var team = getTeamEntity(organizationId, teamId);
        teamRepository.delete(team);
    }
    
    @Transactional
    public TeamRes saveTeam(Long id, TeamReq req) {
        var organization = getEntity(id);
        var team = new Team(req.getName(), req.getOrderNum(), req.getParentsId(), organization);
        teamRepository.save(team);
        return toTeamRes(team);
    }
    
    private Organization getEntity(Long id) {
        return organizationRepository.findById(id)
            .orElseThrow(() -> new BaseException(ErrorCode.ORGANIZATION_NOT_FOUND, id));
    }
    
    private Team getTeamEntity(Long organizationId, Long teamId) {
        var organization = getEntity(organizationId);
        return teamRepository.findByOrganizationAndId(organization, teamId)
            .orElseThrow(() -> new BaseException(ErrorCode.ORGANIZATION_HAS_NOT_TEAM, teamId));
    }
    
    private OrganizationRes getRes(Long id) {
        return toRes(getEntity(id));
    }
    
    private OrganizationRes toRes(Organization organization) {
        List<TeamRes> teamResList = organization.getTeams() != null
            ? organization.getTeams().stream()
                .map(this::toTeamRes)
                .collect(Collectors.toList())
            : null;
        
        return new OrganizationRes(
            organization.getId(),
            organization.getName(),
            organization.getLogoFileName(),
            organization.getCeo(),
            organization.getTelNumber(),
            organization.getAddress(),
            teamResList
        );
    }
    
    private TeamRes toTeamRes(Team team) {
        return new TeamRes(
            team.getId(),
            team.getName(),
            team.getOrderNum(),
            team.getParents() != null ? team.getParents().getId() : null
        );
    }
    
    private MemberRes toMemberRes(Member member) {
        return new MemberRes(
            member.getId(),
            member.getName(),
            member.getEmail(),
            member.getTeam() != null ? member.getTeam().getName() : null,
            member.getPermission(),
            member.getState()
        );
    }
}
