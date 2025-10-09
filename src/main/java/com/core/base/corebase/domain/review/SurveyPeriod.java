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
public class SurveyPeriod {
    private LocalDate surveyStartDate;
    private LocalDate surveyEndDate;

    public boolean isBefore(LocalDate date) {
        return surveyStartDate.isBefore(date);
    }

    public boolean between(LocalDate date) {
        return surveyStartDate.isEqual(date) ||
               surveyEndDate.isEqual(date) ||
               (surveyStartDate.isBefore(date) && surveyEndDate.isAfter(date));
    }
}
