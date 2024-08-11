package com.core.base.corebase.controller.user.dto

import java.util.*

data class UserOrganizationRes(
    val participationOrganizations: List<UserOrganization>,
    val invitedOrganizations: List<UserOrganization>
) {
    data class UserOrganization(
        val id: UUID,
        val logo: String?,
        val name: String,
    )
}
