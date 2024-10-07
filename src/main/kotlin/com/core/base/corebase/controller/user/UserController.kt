package com.core.base.corebase.controller.user

import com.core.base.corebase.config.AuthenticationFacade
import com.core.base.corebase.controller.user.dto.UserOrganizationRes
import com.core.base.corebase.controller.user.dto.UserRes
import com.core.base.corebase.service.user.UserService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@Tag(name = "유저 API")
@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService,
    private val authenticationFacade: AuthenticationFacade
) {

    @GetMapping
    fun getUser(): UserRes = userService.getUser(authenticationFacade.uid)

    @GetMapping("/organizations")
    fun getUserOrganization(): UserOrganizationRes = userService.getUserOrganization(authenticationFacade.uid)

}