package com.core.base.corebase.common.code

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val code: HttpStatus,
    val reason: String,
    val detail: String
) {
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Invalid Token", "(token: {VALUE})"),
    ORGANIZATION_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 회사가 존재하지 않습니다.", "(id: {VALUE})"),
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 리뷰가 존재하지 않습니다.", "(id: {VALUE})"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 유저가 존재하지 않습니다.", "(uid: {VALUE})"),
    REVIEWER_NOT_ALLOWED(HttpStatus.FORBIDDEN, "해당 리뷰를 작성할 권한이 없습니다.", "(id: {VALUE})"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류가 발생하였습니다.", "(message: {VALUE})");
}