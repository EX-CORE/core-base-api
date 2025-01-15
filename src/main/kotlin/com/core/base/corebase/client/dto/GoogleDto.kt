package com.core.base.corebase.client.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

object GoogleDto {

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    class GoogleTokenReq(
        val code: String,
        val clientId: String,
        val clientSecret: String,
        val redirectUri: String,
        val grantType: String
    )

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    class GoogleTokenRefreshReq(
        val clientId: String,
        val clientSecret: String,
        val refreshToken: String,
        val grantType: String
    )

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    class GoogleTokenRes(
        val accessToken: String,
        val expiresIn: Int,
        val tokenType: String,
        val scope: String,
        val refreshToken: String,
        val idToken: String?
    )

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    class GoogleInfoRes(
        val sub: String,
        val name: String,
        val givenName: String,
        val familyName: String,
        val picture: String,
        val email: String,
        val emailVerified: String,
        val locale: String?
    )

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    class GoogleTokenRefreshRes(
        val accessToken: String,
        val expiresIn: String,
        val tokenType: String,
        val scope: String
    )

    /*
    * 캘린더 DTO들에서 빠진 속성들이 많고 무조건 써야하는 필드 빼고는 대다수를 nullable하게 처리함
    * 참고: https://developers.google.com/calendar/api/v3/reference/events/list
     */
    class GoogleCalendarRes(
        val kind: String?,
        val etag: String?,
        val summary: String?,
        val updated: String?,
        val timeZone: String?,
        val accessRole: String?,
        val defaultReminders: List<GoogleCalendarDefaultReminderRes>?,
        val items: List<GoogleCalendarItemRes>
    )

    class GoogleCalendarDefaultReminderRes(
        val method: String,
        val minutes: Int
    )

    class GoogleCalendarItemRes(
        val kind: String?,
        val etag: String?,
        val id: String,
        val status: String?,
        val htmlLink: String?,
        @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ss.SSSZ")
        val created: LocalDateTime?,
        @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ss.SSSZ")
        val updated: LocalDateTime?,
        val summary: String?,
        val location: String?,
        val creator: GoogleCalendarItemPeopleRes?,
        val organizer: GoogleCalendarItemPeopleRes?,
        val start: GoogleCalendarItemTimeRes,
        val end: GoogleCalendarItemTimeRes,
        val recurringEventId: String?,
        val originalStartTime: GoogleCalendarItemTimeRes?,
        val iCalUID: String?,
        val sequence: Int?,
        val attendees: List<GoogleCalendarItemAttendeeRes>?,
        val hangoutLink: String?,
        val conferenceData: Any?,
        val reminders: GoogleCalendarItemRemindersRes?,
        val eventType: String?
    )

    class GoogleCalendarItemPeopleRes(
        val email: String,
        val displayName: String?
    )

    class GoogleCalendarItemTimeRes(
        val date: String?,
        val dateTime: String?,
        val timeZone: String?
    )

    class GoogleCalendarItemAttendeeRes(
        val email: String,
        val displayName: String?
    )

    class GoogleCalendarItemRemindersRes(
        val useDefault: Boolean
    )

}