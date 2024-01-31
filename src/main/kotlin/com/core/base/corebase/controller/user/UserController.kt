package com.core.base.corebase.controller.user

import com.core.base.corebase.controller.user.dto.UserReq
import com.core.base.corebase.domain.review.Review
import com.core.base.corebase.domain.user.User
import com.core.base.corebase.repository.UserRepository
import com.core.base.corebase.service.review.ReviewService
import com.core.base.corebase.service.user.UserService
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/users")
class UserController(
    val userService: UserService
) {
    @PostMapping
    fun save(@RequestBody userReq: UserReq) : User = userService.save(userReq)
}