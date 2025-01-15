package com.core.base.corebase.client

import com.core.base.corebase.client.dto.GoogleDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(value = "googleAuthClient", url = "https://oauth2.googleapis.com")
interface GoogleAuthClient {

    @PostMapping("/token", produces = ["application/json"])
    fun getTokenByCode(@RequestBody request: GoogleDto.GoogleTokenReq): Object

    @PostMapping("/token", produces = ["application/json"])
    fun getTokenByCode2(@RequestBody request: GoogleDto.GoogleTokenReq): GoogleDto.GoogleTokenRes

}