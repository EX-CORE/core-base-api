package com.core.base.corebase.domain.review;

import com.core.base.corebase.domain.review.code.QuestionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "review_question")
public class ReviewQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String question;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuestionType type;

    private Integer range;

    @Column(name = "order_num", nullable = false)
    private Integer orderNum;

    @Column(name = "use_score", nullable = false)
    private Boolean useScore;

    @Column(name = "use_multi_select", nullable = false)
    private Boolean useMultiSelect;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_section_id", nullable = false)
    private ReviewSection reviewSection;

    protected ReviewQuestion() {
        // For JPA
    }

    public ReviewQuestion(String question, QuestionType type, Integer range,
                         Integer orderNum, Boolean useScore,
                         Boolean useMultiSelect, ReviewSection reviewSection) {
        this.question = question;
        this.type = type;
        this.range = range;
        this.orderNum = orderNum;
        this.useScore = useScore;
        this.useMultiSelect = useMultiSelect;
        this.reviewSection = reviewSection;
    }
}
