package com.core.base.corebase.domain.review;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ReviewPeriod {
    private LocalDate reviewStartDate;
    private LocalDate reviewEndDate;

    public boolean isBefore(LocalDate date) {
        return reviewStartDate.isBefore(date);
    }

    public boolean between(LocalDate date) {
        return reviewStartDate.isEqual(date) ||
               reviewEndDate.isEqual(date) ||
               (reviewStartDate.isBefore(date) && reviewEndDate.isAfter(date));
    }
}
