package com.core.base.corebase.common.code

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val code: HttpStatus,
    val reason: String,
    val detail: String
) {
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Invalid Token", "(token: {VALUE})"),
    ORGANIZATION_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 회사가 존재하지 않습니다.", "(id: {VALUE})"),
    ORGANIZATION_TEAM_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 팀 정보가 존재하지 않습니다.", "(id: {VALUE})"),
    ORGANIZATION_TEAM_PARENT_NOT_FOUND(HttpStatus.NOT_FOUND, "상위 팀 정보가 존재하지 않습니다.", "(id: {VALUE})"),
    ORGANIZATION_HAS_NOT_TEAM(HttpStatus.NOT_FOUND, "해당 조직에 설정된 팀이 존재하지 않습니다. ", "(id: {VALUE})"),
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 리뷰가 존재하지 않습니다.", "(id: {VALUE})"),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 구성원이 존재하지 않습니다.", "(id: {VALUE})"),
    MEMBER_EMAIL_DUPLICATE(HttpStatus.CONFLICT, "해당 메일을 가진 구성원이 이미 존재합니다. ", "(email: {VALUE})"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 유저가 존재하지 않습니다.", "(uid: {VALUE})"),
    REVIEWER_NOT_ALLOWED(HttpStatus.FORBIDDEN, "해당 리뷰를 작성할 권한이 없습니다.", "(id: {VALUE})"),
    ANNOUNCEMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "공지사항이 존재하지 않습니다.", "(id: {VALUE})"),
    ANNOUNCEMENT_NOT_ALLOWED(HttpStatus.FORBIDDEN, "해당 공지사항 관련 권한이 없습니다.", "(id: {VALUE})"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류가 발생하였습니다.", "(message: {VALUE})")
}