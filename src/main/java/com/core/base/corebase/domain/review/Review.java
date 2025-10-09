package com.core.base.corebase.domain.review;

import com.core.base.corebase.domain.review.code.ReviewState;
import com.core.base.corebase.domain.user.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_member_id", nullable = false)
    private ReviewMember reviewMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewAnswer> answers = new ArrayList<>();

    protected Review() {
        // For JPA
    }

    public Review(ReviewMember reviewMember, Member member, Review review, ReviewState state) {
        this.reviewMember = reviewMember;
        this.member = member;
        this.review = review;
        this.state = state != null ? state.name() : ReviewState.BEFORE.name();
    }

    // Helper methods for bidirectional relationship
    public void addAnswer(ReviewAnswer answer) {
        answers.add(answer);
        answer.setReview(this);
    }

    public void removeAnswer(ReviewAnswer answer) {
        answers.remove(answer);
        answer.setReview(null);
    }

    public ReviewState getState() {
        if(this.state == null) {
            return null;
        }
        return ReviewState.valueOf(state);
    }
}
