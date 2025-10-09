package com.core.base.corebase.domain.review;

import com.core.base.corebase.domain.user.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "review_member")
public class ReviewMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    protected ReviewMember() {
        // For JPA
    }

    public ReviewMember(Member member, Review review) {
        this.member = member;
        this.review = review;
    }

}
