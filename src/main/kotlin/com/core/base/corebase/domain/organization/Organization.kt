package com.core.base.corebase.domain.organization

import com.core.base.corebase.common.code.ErrorCode
import com.core.base.corebase.common.exception.BaseException
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.*

@Entity(name ="organization")
class Organization(
    var name: String,
    var logoFileName: String?,
    var ceo: String?,
    var telNumber: String?,
    var address: String?,
    val teams: MutableList<Team>? = mutableListOf(),
    @Id val id: UUID = UUID.randomUUID()
) {
    fun addTeam(team: Team) {
        if (teams == null) {
            throw BaseException(ErrorCode.ORGANIZATION_HAS_NOT_TEAM, id)
        } else {
            teams.add(team)
        }
    }

    // 특정 id를 가진 팀을 teams 리스트에서 삭제하는 함수
    fun removeTeamById(teamId: UUID): Boolean {
        if (teams == null) {
            throw BaseException(ErrorCode.ORGANIZATION_HAS_NOT_TEAM, id)
        }
        return teams.removeIf { it.id == teamId }
    }

    fun updateTeamById(teamId: UUID, name: String, order: Int, parentId: UUID?) {
        if (teams == null) {
            throw BaseException(ErrorCode.ORGANIZATION_HAS_NOT_TEAM, id)
        }
        if (parentId != null && teams.indexOfFirst { it.id == parentId } == -1) {
            throw BaseException(ErrorCode.ORGANIZATION_TEAM_PARENT_NOT_FOUND, parentId)
        }
        val index = teams.indexOfFirst { it.id == teamId }
        if (index != -1) {
            teams[index] = Team(name, order, parentId, teamId)
        }
    }
}