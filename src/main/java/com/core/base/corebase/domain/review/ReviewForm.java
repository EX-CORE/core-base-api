package com.core.base.corebase.domain.review;

import com.core.base.corebase.domain.user.Member;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "review_forms")
public class ReviewForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private Member creator;

    @OneToMany(mappedBy = "reviewForm", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewFormSection> sections = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder
    public ReviewForm(String title, String description, Member creator) {
        this.title = title;
        this.description = description;
        this.creator = creator;
    }

    public void addSection(ReviewFormSection section) {
        this.sections.add(section);
        section.setReviewForm(this);
    }
}
