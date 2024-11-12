package com.core.base.corebase.domain.user

import com.core.base.corebase.domain.user.code.UserState
import jakarta.persistence.*
import java.util.*

@Entity(name = "accounts")
class Account(
    refreshToken: String,
    state: UserState,
    user: User
) {
    @Id
    @Column(name = "uid", insertable = false, updatable = false, columnDefinition = "BINARY(16)")
    var uid: UUID = user.uid

    @MapsId("uid")
    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.MERGE])
    @JoinColumn(name = "uid", referencedColumnName = "uid", insertable = false, updatable = false)
    var user: User = user; protected set

    var state: UserState = state; protected set

    var refreshToken: String = refreshToken;


}