package com.core.base.corebase.controller.organization.dto

import java.time.LocalDate
import java.util.*

class AnnouncementRes(
    val id: UUID,
    val title: String,
    val content: String,
    val createdAt: LocalDate
) {
}