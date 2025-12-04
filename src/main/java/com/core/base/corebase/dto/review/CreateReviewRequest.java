// Update CreateReviewRequest.java
package com.core.base.corebase.dto.review;

import com.core.base.corebase.domain.review.ParticipantRole;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record CreateReviewRequest(
    @NotBlank(message = "리뷰 제목은 필수 입력값입니다.")
    String title,
    
    @NotNull(message = "시작일은 필수 입력값입니다.")
    LocalDate startDate,
    
    @NotNull(message = "종료일은 필수 입력값입니다.")
    LocalDate endDate,
    
    @NotNull(message = "리뷰 양식 ID는 필수 입력값입니다.")
    Long reviewFormId,
    
    @NotNull(message = "참여자 목록은 필수 입력값입니다.")
    List<@Valid ParticipantDto> participants
) {
    public record ParticipantDto(
        @NotNull(message = "사용자 ID는 필수 입력값입니다.")
        Long userId,
        
        @NotNull(message = "역할은 필수 입력값입니다.")
        ParticipantRole role
    ) {}
}