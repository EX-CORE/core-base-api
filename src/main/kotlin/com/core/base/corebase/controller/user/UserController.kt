package com.core.base.corebase.controller.user

import com.core.base.corebase.config.AuthenticationFacade
import com.core.base.corebase.controller.user.dto.UserOrganizationRes
import com.core.base.corebase.controller.user.dto.UserReq
import com.core.base.corebase.domain.user.User
import com.core.base.corebase.service.user.UserService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import java.util.*


@Tag(name = "유저 API")
@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService,
    private val authenticationFacade: AuthenticationFacade
) {

    @PostMapping("organization/{organizationId}")
    fun save(@PathVariable organizationId: UUID,
             @RequestBody userReqList: List<UserReq>) : List<User> = userService.save(organizationId, userReqList)

    @GetMapping("/organizations")
    fun getUserOrganization(): UserOrganizationRes = userService.getUserOrganization(authenticationFacade.uid)

}