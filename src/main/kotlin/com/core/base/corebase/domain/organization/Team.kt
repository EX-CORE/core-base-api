package com.core.base.corebase.domain.organization

import jakarta.persistence.*
import net.huray.backend.minuting.entity.common.BaseDateTimeEntity

@Entity(name ="team")
class Team(
    name: String,
    order: Int,
    parentId: Long?,
    organization: Organization
): BaseDateTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L

    var name = name; protected set
    var order = order; protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    var parents: Team? = null
        protected set

    @Column(name = "parent_id", nullable = true)
    var parentId: Long? = parentId

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    var organization: Organization = organization; protected set


    fun update(name: String, order: Int, parentId: Long?) {
        this.name = name
        this.order = order
        this.parentId = parentId
    }
}