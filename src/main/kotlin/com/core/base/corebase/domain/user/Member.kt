package com.core.base.corebase.domain.user

import com.core.base.corebase.domain.user.code.MemberState
import com.core.base.corebase.domain.user.code.PermissionType
import jakarta.persistence.Id
import jakarta.persistence.Entity
import java.util.*

@Entity( name = "member")
class Member(
    var email: String,
    var name: String,
    var uid: UUID?, //User
    var organizationId : UUID, //Organization
    var teamId: UUID?, //Team
    var permission: PermissionType,
    var state: MemberState = MemberState.WAIT,
    @Id val id: UUID = UUID.randomUUID()
) {
    fun isWait(): Boolean = state.equals(MemberState.WAIT)
    fun updateJoin(uid: UUID) {
        this.uid = uid
        this.state = MemberState.JOIN
    }
}
