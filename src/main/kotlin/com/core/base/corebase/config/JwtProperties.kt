package com.core.base.corebase.config

import org.springframework.boot.context.properties.ConfigurationProperties
import java.util.*

@ConfigurationProperties(prefix = "auth.jwt")
class JwtProperties(secretKey: String, accessExp: Long, refreshExp: Long) {
    val secretKey: String
    val accessExp: Long
    val refreshExp: Long

    init {
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.toByteArray())
        this.accessExp = accessExp
        this.refreshExp = refreshExp
    }
}