package com.core.base.corebase.client;

import com.core.base.corebase.client.dto.GoogleDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "googleAuthClient", url = "https://oauth2.googleapis.com")
public interface GoogleAuthClient {

    @PostMapping(value = "/token", produces = "application/json")
    GoogleDto.GoogleTokenRes getTokenByCode(@RequestBody GoogleDto.GoogleTokenReq request);
}
