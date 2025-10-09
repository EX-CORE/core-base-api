package com.core.base.corebase.domain.user;

import com.core.base.corebase.domain.organization.Organization;
import com.core.base.corebase.domain.organization.Team;
import com.core.base.corebase.domain.user.code.MemberState;
import com.core.base.corebase.domain.user.code.PermissionType;
import com.core.base.corebase.domain.user.code.UserState;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "member")
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String permission;

    @Column(nullable = false)
    private String state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid")
    private User user;

    protected Member() {
        // For JPA
    }

    public Member(String email, String name, User user,
                 Organization organization, Team team,
                 PermissionType permission, MemberState state) {
        this.email = email;
        this.name = name;
        this.user = user;
        this.organization = organization;
        this.team = team;
        this.permission = permission.name();
        this.state = state != null ? state.name() : MemberState.WAIT.name();
    }

    public boolean isWait() {
        return MemberState.WAIT.equals(getState());
    }

    public void updateJoin(User user) {
        this.user = user;
        this.state = MemberState.JOIN.name();
    }

    public void updateUser(User user) {
        this.user = user;
    }

    public MemberState getState() {
        if(state == null) {
            return null;
        }
        return MemberState.valueOf(state);
    }

    public PermissionType getPermission() {
        if(permission == null) {
            return null;
        }
        return PermissionType.valueOf(permission);
    }
}
