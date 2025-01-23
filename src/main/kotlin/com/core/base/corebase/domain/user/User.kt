package com.core.base.corebase.domain.user

import com.core.base.corebase.domain.organization.Team
import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import java.util.*

@Entity( name = "users")
class User(
    name: String,
    email: String,
    profile: String,
){
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "uuid")
    var uid: UUID = UUID.randomUUID()

    var name = name; protected set

    @Column(unique = true)
    var email = email; protected set

    var profile = profile; protected set

    @OneToOne(mappedBy = "user")
    lateinit var account: Account// = account; protected set

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL])
    protected val mutableMembers: MutableList<Member> = mutableListOf()
    val members: List<Member> get() = mutableMembers.toList()
}