// src/main/java/com/core/base/corebase/domain/review/RatingOption.java
package com.core.base.corebase.domain.review;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "rating_options")
public class RatingOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String label;

    @Column(nullable = false)
    private int score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private ReviewQuestion question;

    @Builder
    public RatingOption(String label, int score, ReviewQuestion question) {
        this.label = label;
        this.score = score;
        this.question = question;
    }
}