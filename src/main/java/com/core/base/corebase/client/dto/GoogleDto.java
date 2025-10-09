package com.core.base.corebase.client.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

public class GoogleDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class GoogleTokenReq {
        private String code;
        private String clientId;
        private String clientSecret;
        private String redirectUri;
        private String grantType;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class GoogleTokenRefreshReq {
        private String clientId;
        private String clientSecret;
        private String refreshToken;
        private String grantType;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class GoogleTokenRes {
        private String accessToken;
        private int expiresIn;
        private String tokenType;
        private String scope;
        private String refreshToken;
        private String idToken;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class GoogleInfoRes {
        private String sub;
        private String name;
        private String givenName;
        private String familyName;
        private String picture;
        private String email;
        private String emailVerified;
        private String locale;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class GoogleTokenRefreshRes {
        private String accessToken;
        private String expiresIn;
        private String tokenType;
        private String scope;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GoogleCalendarRes {
        private String kind;
        private String etag;
        private String summary;
        private String updated;
        private String timeZone;
        private String accessRole;
        private List<GoogleCalendarDefaultReminderRes> defaultReminders;
        private List<GoogleCalendarItemRes> items;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GoogleCalendarDefaultReminderRes {
        private String method;
        private int minutes;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GoogleCalendarItemRes {
        private String kind;
        private String etag;
        private String id;
        private String status;
        private String htmlLink;

        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        private LocalDateTime created;

        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        private LocalDateTime updated;

        private String summary;
        private String location;
        private GoogleCalendarItemPeopleRes creator;
        private GoogleCalendarItemPeopleRes organizer;
        private GoogleCalendarItemTimeRes start;
        private GoogleCalendarItemTimeRes end;
        private String recurringEventId;
        private GoogleCalendarItemTimeRes originalStartTime;
        private String iCalUID;
        private Integer sequence;
        private List<GoogleCalendarItemAttendeeRes> attendees;
        private String hangoutLink;
        private Object conferenceData;
        private GoogleCalendarItemRemindersRes reminders;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GoogleCalendarItemPeopleRes {
        private String email;
        private String displayName;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GoogleCalendarItemTimeRes {
        private String dateTime;
        private String timeZone;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GoogleCalendarItemAttendeeRes {
        private String email;
        private String displayName;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GoogleCalendarItemRemindersRes {
        private boolean useDefault;
        private List<Object> overrides;
    }
}
