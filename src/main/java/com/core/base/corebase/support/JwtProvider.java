package com.core.base.corebase.support;

import com.core.base.corebase.common.code.ErrorCode;
import com.core.base.corebase.common.exception.BaseException;
import com.core.base.corebase.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class JwtProvider {

    private final JwtProperties jwtProperties;

    public JwtProvider(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    public enum TokenType { ACCESS, REFRESH }

    public String generateAccessToken(UUID id) {
        return generateToken(id, TokenType.ACCESS);
    }

    public String generateRefreshToken(UUID id) {
        return generateToken(id, TokenType.REFRESH);
    }

    private String generateToken(UUID id, TokenType type) {
        return Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getAccessExp()))
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecretKey())
                .setIssuedAt(new Date())
                .setSubject(id.toString())
                .claim("type", type.name())
                .compact();
    }

    public Claims getBody(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new BaseException(ErrorCode.INVALID_TOKEN, token);
        }
    }

    public boolean isAccess(Claims body) {
        return TokenType.ACCESS.name().equals(body.get("type", String.class));
    }

    public boolean isRefresh(Claims body) {
        return TokenType.REFRESH.name().equals(body.get("type", String.class));
    }

    public UUID getId(Claims body) {
        return UUID.fromString(body.getSubject());
    }
}
