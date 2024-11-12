package com.core.base.corebase.controller.organization.dto

import java.time.LocalDateTime

class AnnouncementRes(
    val id: Long,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime
) {
}