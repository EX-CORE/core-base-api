package com.core.base.corebase.controller.base

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@Tag(name = "로그인 API")
@RestController
class BaseController {

    @GetMapping("/health-check")
    fun healthCheck() = LocalDateTime.now().toString()

}