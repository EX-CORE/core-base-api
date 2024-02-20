package com.core.base.corebase.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "oauth.google")
class GoogleProperties(
    val clientId: String,
    val clientSecret: String,
    val redirectUrl: String,
)