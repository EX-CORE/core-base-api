package com.core.base.corebase.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Base64;

@ConfigurationProperties(prefix = "auth.jwt")
public class JwtProperties {
    private final String secretKey;
    private final long accessExp;
    private final long refreshExp;

    public JwtProperties(String secretKey, long accessExp, long refreshExp) {
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        this.accessExp = accessExp * 1000L;
        this.refreshExp = refreshExp * 1000L;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public long getAccessExp() {
        return accessExp;
    }

    public long getRefreshExp() {
        return refreshExp;
    }
}
