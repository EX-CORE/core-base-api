package com.core.base.corebase.domain.review;

import com.core.base.corebase.common.code.ErrorCode;
import com.core.base.corebase.common.exception.BaseException;
import com.core.base.corebase.domain.organization.Organization;
import com.core.base.corebase.domain.review.code.StateType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "review_base")
public class ReviewBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Embedded
    private SurveyPeriod surveyPeriod;

    @Embedded
    private ReviewPeriod reviewPeriod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @OneToMany(mappedBy = "reviewBase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewSection> sections = new ArrayList<>();

    @OneToMany(mappedBy = "reviewBase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewChoice> defaultScoreChoices = new ArrayList<>();

    private String secretKey;

    @Enumerated(EnumType.STRING)
    private StateType state;

    protected ReviewBase() {
        // For JPA
    }

    public ReviewBase(String title, String description, SurveyPeriod surveyPeriod,
                     ReviewPeriod reviewPeriod, Organization organization,
                     String secretKey, StateType state) {
        this.title = title;
        this.description = description;
        this.surveyPeriod = surveyPeriod;
        this.reviewPeriod = reviewPeriod;
        this.organization = organization;
        this.secretKey = secretKey;
        this.state = state;
    }

    public void stop() {
        if (!validStop()) {
            throw new BaseException(ErrorCode.REVIEW_NOT_FOUND, id);
        }
        this.state = StateType.STOPPED;
    }

    private boolean validStop() {
        if (state.isInActive()) {
            return false;
        }
        LocalDate today = LocalDate.now();
        return surveyPeriod.isBefore(today) || surveyPeriod.between(today);
    }

    // Helper methods for bidirectional relationships
    public void addSection(ReviewSection section) {
        sections.add(section);
        section.setReviewBase(this);
    }

    public void removeSection(ReviewSection section) {
        sections.remove(section);
        section.setReviewBase(null);
    }

    public void addDefaultScoreChoice(ReviewChoice choice) {
        defaultScoreChoices.add(choice);
        choice.setReviewBase(this);
    }

    public void removeDefaultScoreChoice(ReviewChoice choice) {
        defaultScoreChoices.remove(choice);
        choice.setReviewBase(null);
    }
}
