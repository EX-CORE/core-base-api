package com.core.base.corebase.domain.user;

import com.core.base.corebase.domain.organization.Organization;
import com.core.base.corebase.domain.organization.Team;
import com.core.base.corebase.domain.user.code.MemberState;
import com.core.base.corebase.domain.user.code.PermissionType;
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PermissionType permission;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberState state;

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
        this.permission = permission;
        this.state = state != null ? state : MemberState.WAIT;
    }

    public boolean isWait() {
        return MemberState.WAIT.equals(state);
    }

    public void updateJoin(User user) {
        this.user = user;
        this.state = MemberState.JOIN;
    }

    public void updateUser(User user) {
        this.user = user;
    }
}
