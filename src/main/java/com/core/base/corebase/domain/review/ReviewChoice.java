package com.core.base.corebase.domain.review;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Builder
@AllArgsConstructor
@Table(name = "review_choice")
public class ReviewChoice {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String label;
    
    @Column(name = "order_num", nullable = false)
    private Integer orderNum;
    
    private Integer score;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_answer_id", nullable = false)
    private ReviewAnswer reviewAnswer;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_base_id", nullable = false)
    private ReviewBase reviewBase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_question_id", nullable = false)
    private ReviewQuestion reviewQuestion;
    
    protected ReviewChoice() {
        // For JPA
    }
    
    public ReviewChoice(String label, Integer orderNum, Integer score, 
                       ReviewAnswer reviewAnswer, ReviewBase reviewBase) {
        this.label = label;
        this.orderNum = orderNum;
        this.score = score;
        this.reviewAnswer = reviewAnswer;
        this.reviewBase = reviewBase;
    }
}
