package com.core.base.corebase.domain.review;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "review_answer")
public class ReviewAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    @OneToMany(mappedBy = "reviewAnswer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewChoice> choices = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private ReviewQuestion question;

    protected ReviewAnswer() {
        // For JPA
    }

    public ReviewAnswer(Review review, ReviewQuestion question) {
        this.review = review;
        this.question = question;
    }

    // Helper methods for bidirectional relationship
    public void addChoice(ReviewChoice choice) {
        choices.add(choice);
        choice.setReviewAnswer(this);
    }

    public void removeChoice(ReviewChoice choice) {
        choices.remove(choice);
        choice.setReviewAnswer(null);
    }
}
