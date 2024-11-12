package com.core.base.corebase.domain.user

import com.core.base.corebase.domain.organization.Organization
import com.core.base.corebase.domain.organization.Team
import com.core.base.corebase.domain.user.code.MemberState
import com.core.base.corebase.domain.user.code.PermissionType
import jakarta.persistence.*

@Entity( name = "member")
class Member(
    email: String,
    name: String,
    user: User?, //User
    organization : Organization, //Organization
    team: Team?, //Team
    permission: PermissionType,
    state: MemberState = MemberState.WAIT
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L

    var name = name; protected set
    var email = email; protected set
    var permission = permission; protected set

    @Enumerated(EnumType.STRING)
    var state = state; protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = true)
    var team: Team? = team; protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    var organization: Organization = organization; protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid", nullable = true)
    var user: User? = user; protected set


    fun isWait(): Boolean = state.equals(MemberState.WAIT)
    fun updateJoin(user: User) {
        this.user = user
        this.state = MemberState.JOIN
    }
}
