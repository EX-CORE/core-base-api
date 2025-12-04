// Update Review.java
package com.core.base.corebase.domain.review;

import com.core.base.corebase.domain.user.Member;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ReviewStatus status = ReviewStatus.SCHEDULED;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private Member creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_form_id", nullable = false)
    private ReviewForm reviewForm;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewParticipant> participants = new ArrayList<>();


    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder
    public Review(String name, LocalDate startDate, LocalDate endDate, Member creator, ReviewForm reviewForm) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.creator = creator;
        this.reviewForm = reviewForm;
        updateStatus();
    }

    public void updateStatus() {
        LocalDate today = LocalDate.now();
        if (today.isBefore(startDate)) {
            this.status = ReviewStatus.SCHEDULED;
        } else if (today.isAfter(endDate)) {
            this.status = ReviewStatus.COMPLETED;
        } else {
            this.status = ReviewStatus.IN_PROGRESS;
        }
    }

    public void addParticipant(ReviewParticipant participant) {
        this.participants.add(participant);
    }
}