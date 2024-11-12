package com.core.base.corebase.domain.organization

import jakarta.persistence.*
import net.huray.backend.minuting.entity.common.BaseDateTimeEntity

@Entity(name ="organization")
class Organization(
    name: String,
    logoFileName: String?,
    ceo: String?,
    telNumber: String?,
    address: String?,
) : BaseDateTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L

    var name = name; protected set
    var logoFileName = logoFileName; protected set
    var ceo = ceo; protected set
    var telNumber = telNumber; protected set
    var address = address; protected set

    @OneToMany(mappedBy = "organization", cascade = [CascadeType.ALL])
    protected val mutableTeams: MutableList<Team> = mutableListOf()
    val teams: List<Team> get() = mutableTeams.toList()

    fun update(
        name: String?,
        logoFileName: String?,
        ceo: String?,
        telNumber: String?,
        address: String?,
    ) {
        this.name = name ?: this.name
        this.logoFileName = logoFileName ?: this.logoFileName
        this.ceo = ceo ?: this.ceo
        this.telNumber = telNumber ?: this.telNumber
        this.address = address ?: this.address
    }
}