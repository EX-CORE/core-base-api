package net.huray.backend.minuting.entity.common

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@EntityListeners(AuditingEntityListener::class)
@MappedSuperclass
abstract class BaseDateTimeEntity {

    @CreatedDate
    @Column(updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.of(1970, 1, 1, 0, 0, 1)!!

    @LastModifiedDate
    var updatedAt: LocalDateTime = LocalDateTime.of(1970, 1, 1, 0, 0, 1)!!

}
