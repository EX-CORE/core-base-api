package com.core.base.corebase.domain.organization;

import com.core.base.corebase.domain.common.BaseDateTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "team")
public class Team extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "order_num", nullable = false)
    private Integer orderNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    private Team parents;

    @Column(name = "parent_id")
    private Long parentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    protected Team() {
        // For JPA
    }

    public Team(String name, Integer orderNum, Long parentId, Organization organization) {
        this.name = name;
        this.orderNum = orderNum;
        this.parentId = parentId;
        this.organization = organization;
    }

    public void update(String name, Integer orderNum, Long parentId) {
        this.name = name;
        this.orderNum = orderNum;
        this.parentId = parentId;
    }

    public void update(Integer orderNum, Long parentId) {
        this.orderNum = orderNum;
        this.parentId = parentId;
    }
}
