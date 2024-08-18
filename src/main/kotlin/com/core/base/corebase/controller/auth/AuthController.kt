package com.core.base.corebase.controller.auth

import com.core.base.corebase.client.dto.AuthDto
import com.core.base.corebase.service.auth.AuthService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Tag(name = "로그인 API")
@Controller
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {

    @GetMapping("/code")
    fun moveUserGoogleCode(): String =
        "redirect:${authService.getUserGoogleCode()}"

    @ResponseBody
    @PostMapping("/login")
    fun login(@RequestParam("code") code: String): AuthDto.LoginRes =
        authService.login(code)

    @ResponseBody
    @PostMapping("/token-refresh")
    fun tokenRefresh(@RequestBody req: AuthDto.TokenRefreshReq): AuthDto.TokenRefreshRes =
        authService.tokenRefresh(req)

}