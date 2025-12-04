package com.core.base.corebase.domain.review;

import com.core.base.corebase.domain.common.BaseDateTimeEntity;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Builder
@AllArgsConstructor
@Table(name = "review_section")
public class ReviewSection extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private ReviewBase reviewBase;

    @OneToMany(mappedBy = "reviewSection", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewQuestion> questions = new ArrayList<>();

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
