package com.core.base.corebase.domain.review;

import com.core.base.corebase.domain.common.BaseDateTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "review_section")
public class ReviewSection extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private ReviewBase reviewBase;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "order_num", nullable = false)
    private Integer orderNum;

    @Column(name = "is_use", nullable = false)
    private Boolean isUse;

    protected ReviewSection() {
        // For JPA
    }

    public ReviewSection(ReviewBase reviewBase, String title, String description,
                        Integer orderNum, Boolean isUse) {
        this.reviewBase = reviewBase;
        this.title = title;
        this.description = description;
        this.orderNum = orderNum;
        this.isUse = isUse;
    }
}
