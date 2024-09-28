package com.core.base.corebase.controller.organization.dto

import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class AnnouncementRes(
    val id: UUID,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime
) {
}