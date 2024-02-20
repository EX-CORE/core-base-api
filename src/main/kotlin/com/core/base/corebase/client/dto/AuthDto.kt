package com.core.base.corebase.client.dto

object AuthDto {

    class LoginRes(
        val accessToken: String,
        val refreshToken: String
    )

    class TokenRefreshReq(
        val refreshToken: String
    )

    class TokenRefreshRes(
        val accessToken: String
    )

}