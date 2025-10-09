package com.core.base.corebase.controller.auth;

import com.core.base.corebase.client.dto.AuthDto;
import com.core.base.corebase.service.auth.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Tag(name = "로그인 API")
@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/code")
    public String moveUserGoogleCode() {
        return "redirect:" + authService.getUserGoogleCode();
    }

    @ResponseBody
    @PostMapping("/login")
    public AuthDto.LoginRes login(
            @RequestParam("code") String code,
            @RequestParam("type") String type) {
        return authService.login(code, type);
    }

    @ResponseBody
    @PostMapping("/token-refresh")
    public AuthDto.TokenRefreshRes tokenRefresh(@RequestBody AuthDto.TokenRefreshReq req) {
        return authService.tokenRefresh(req);
    }
}
