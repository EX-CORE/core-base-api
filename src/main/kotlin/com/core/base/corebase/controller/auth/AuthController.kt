package com.core.base.corebase.controller.auth

import com.core.base.corebase.client.dto.AuthDto
import com.core.base.corebase.common.exception.code.ServerType
import com.core.base.corebase.service.auth.AuthService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletResponse
import org.apache.naming.ResourceRef.AUTH
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Tag(name = "로그인 API")
@Controller
class AuthController(
    private val authService: AuthService
) {

    @GetMapping("${AUTH}/code")
    fun moveUserGoogleCode(@RequestParam("type") type: ServerType): String =
        "redirect:${authService.getUserGoogleCode(type)}"

    @ResponseBody
    @PostMapping("${AUTH}/login")
    fun login(@RequestParam("code") code: String, @RequestParam("type") type: ServerType): AuthDto.LoginRes =
        authService.login(code, type)

    @ResponseBody
    @PostMapping("${AUTH}/token-refresh")
    fun tokenRefresh(@RequestBody req: AuthDto.TokenRefreshReq): AuthDto.TokenRefreshRes =
        authService.tokenRefresh(req)

}