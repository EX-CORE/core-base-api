package com.core.base.corebase.domain.review;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "review_form_sections")
public class ReviewFormSection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_form_id", nullable = false)
    private ReviewForm reviewForm;

    @Column(name = "display_order", nullable = false)
    private int displayOrder;

    // src/main/java/com/core/base/corebase/domain/review/ReviewFormSection.java
    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewQuestion> questions = new ArrayList<>();

    @Builder
    public ReviewFormSection(String title, String description, int displayOrder) {
        this.title = title;
        this.description = description;
        this.displayOrder = displayOrder;
    }

    public void setReviewForm(ReviewForm reviewForm) {
        this.reviewForm = reviewForm;
    }

}
