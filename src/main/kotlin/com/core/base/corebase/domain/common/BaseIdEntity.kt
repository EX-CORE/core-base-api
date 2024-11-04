package net.huray.backend.minuting.entity.common

import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PostLoad
import jakarta.persistence.PostPersist
import org.hibernate.proxy.HibernateProxy
import org.springframework.data.domain.Persistable
import java.util.*

@MappedSuperclass
abstract class BaseIdEntity : Persistable<UUID> {

    @Id
//    @Column(columnDefinition = "VARCHAR(36)")
    private val id: UUID = UUID.randomUUID()

    @Transient
    private var _isNew = true

    override fun getId(): UUID = id

    override fun isNew(): Boolean = _isNew

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is HibernateProxy && this::class != other::class) return false

        return id == getIdentifier(other)
    }

    private fun getIdentifier(obj: Any) =
        if (obj is HibernateProxy) obj.hibernateLazyInitializer.identifier
        else (obj as BaseIdEntity).id

    override fun hashCode() = Objects.hashCode(id)

    @PostPersist
    @PostLoad
    protected fun load() {
        _isNew = false
    }

}