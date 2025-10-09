package com.core.base.corebase.domain.review;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
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
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_answer_id", nullable = false)
    private ReviewAnswer reviewAnswer;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_base_id", nullable = false)
    private ReviewBase reviewBase;
    
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
