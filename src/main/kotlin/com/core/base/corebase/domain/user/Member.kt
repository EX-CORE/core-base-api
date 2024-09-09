package com.core.base.corebase.domain.user

import com.core.base.corebase.domain.user.code.MemberState
import com.core.base.corebase.domain.user.code.PermissionType
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document("member")
class Member(
    var email: String,
    var name: String,
    var uid: UUID?, //User
    var organizationId : UUID, //Organization
    var teamId: UUID?, //Team
    var state: MemberState = MemberState.WAIT,
    var permission: PermissionType,
    @Id val id: UUID = UUID.randomUUID()
) {
    fun isWait(): Boolean = state.equals(MemberState.WAIT)
    fun updateJoin(uid: UUID) {
        this.uid = uid
        this.state = MemberState.JOIN
    }
}
