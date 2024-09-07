package com.core.base.corebase.controller.user.dto

import com.core.base.corebase.domain.user.code.PermissionType
import java.util.*

data class UserOrganizationRes(
    val participationOrganizations: List<ParticipationUserOrganization>,
    val invitedOrganizations: List<InvitedUserOrganization>
) {
    data class ParticipationUserOrganization(
        val id: UUID,
        val logo: String?,
        val name: String?,
        val permission: PermissionType
    )

    data class InvitedUserOrganization(
        val id: UUID,
        val logo: String?,
        val name: String?
    )
}
