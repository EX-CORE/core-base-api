package com.core.base.corebase.domain.review;

import com.core.base.corebase.domain.review.code.QuestionType;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "review_question")
public class ReviewQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String question;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private boolean required;

    @Column(nullable = false)
    private String type;

    private Integer range;

    @Column(name = "order_num", nullable = false)
    private Integer orderNum;

    @Column(name = "use_score", nullable = false)
    private Boolean useScore;

    @Column(name = "use_multi_select", nullable = false)
    private Boolean useMultiSelect;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id", nullable = false)
    private ReviewFormSection section;  // Changed from reviewSection to section

    // Update the constructor parameter
    public ReviewQuestion(String question, QuestionType type, Integer range,
        Integer orderNum, Boolean useScore,
        Boolean useMultiSelect, ReviewFormSection section) {  // Changed parameter name
        this.question = question;
        this.type = type.name();
        this.range = range;
        this.orderNum = orderNum;
        this.useScore = useScore;
        this.useMultiSelect = useMultiSelect;
        this.section = section;  // Update this line
    }

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RatingOption> ratingOptions = new ArrayList<>();

    public QuestionType getQuestionType() {
        if(this.type == null) {
            return null;
        }
        return QuestionType.valueOf(type);
    }
}
