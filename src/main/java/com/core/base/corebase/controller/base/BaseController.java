package com.core.base.corebase.controller.base;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Tag(name = "헬스체크 API")
@RestController
public class BaseController {

    @GetMapping("/health-check")
    public String healthCheck() {
        return LocalDateTime.now().toString();
    }
}
