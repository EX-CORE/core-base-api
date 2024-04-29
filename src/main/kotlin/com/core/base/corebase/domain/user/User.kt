package com.core.base.corebase.domain.user

import com.core.base.corebase.domain.user.code.PermissionType
import com.core.base.corebase.domain.user.code.UserState
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document("user")
class User(
    @Id
    var uid: UUID,
    var name: String,
    var email: String,
    var companyId: UUID?,
    var state: UserState = UserState.WAIT,
    var teamId: UUID?,
    var permission: PermissionType
){
    fun isWait(): Boolean = state.equals(UserState.WAIT)
    fun updateActive() {
        this.state = UserState.ACTIVE
    }


}