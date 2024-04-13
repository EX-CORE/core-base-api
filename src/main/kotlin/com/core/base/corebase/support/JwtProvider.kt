package com.core.base.corebase.support

import com.core.base.corebase.common.exception.BaseException
import com.core.base.corebase.common.code.ErrorCode
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm.HS512
import com.core.base.corebase.config.JwtProperties
import com.core.base.corebase.support.JwtProvider.TokenType.ACCESS
import com.core.base.corebase.support.JwtProvider.TokenType.REFRESH
import org.springframework.stereotype.Component
import java.util.*


@Component
class JwtProvider(
    private val jwtProperties: JwtProperties
) {

    enum class TokenType { ACCESS, REFRESH }

    fun generateAccessToken(id: UUID) = generateToken(id, ACCESS)
    fun generateRefreshToken(id: UUID) = generateToken(id, REFRESH)

    private fun generateToken(id: UUID, type: TokenType): String =
        Jwts.builder()
            .setExpiration(Date(System.currentTimeMillis() + jwtProperties.accessExp))
            .signWith(HS512, jwtProperties.secretKey)
            .setIssuedAt(Date())
            .setSubject(id.toString())
            .claim("type", type.name)
            .compact()

    fun getBody(token: String): Claims {
        return try {
            Jwts.parser().setSigningKey(jwtProperties.secretKey).parseClaimsJws(token).body
        } catch (e: Exception) {
            throw BaseException(ErrorCode.INVALID_TOKEN, token)
        }
    }

    fun isAccess(body: Claims): Boolean = body.get("type", String::class.java) == ACCESS.name
    fun isRefresh(body: Claims): Boolean = body.get("type", String::class.java) == REFRESH.name

    fun getId(body: Claims): UUID = UUID.fromString(body.subject.toString())

}