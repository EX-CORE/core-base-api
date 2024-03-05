package com.core.base.corebase.client

import com.core.base.corebase.client.dto.GoogleDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(value = "googleInfoClient", url = "https://openidconnect.googleapis.com")
interface GoogleInfoClient {

    @GetMapping("/v1/userinfo", produces = ["application/json"])
    fun getInfo(@RequestHeader("Authorization") accessToken: String): GoogleDto.GoogleInfoRes

}