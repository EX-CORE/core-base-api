package com.core.base.corebase.service.user

import com.core.base.corebase.controller.review.dto.QuestionRes
import com.core.base.corebase.controller.review.dto.ReviewRes
import com.core.base.corebase.controller.review.dto.ReviewerRes
import com.core.base.corebase.controller.review.dto.ReviewerSectionRes
import com.core.base.corebase.controller.user.dto.UserReq
import com.core.base.corebase.domain.review.*
import com.core.base.corebase.domain.review.code.ChoiceType
import com.core.base.corebase.domain.review.code.QuestionType
import com.core.base.corebase.domain.user.User
import com.core.base.corebase.repository.ReviewRepository
import com.core.base.corebase.repository.ReviewerRepository
import com.core.base.corebase.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    val userRepository: UserRepository,
) {
    fun save(req: UserReq): User =
        userRepository.save(
            User(UUID.randomUUID(), req.email, req.name, req.companyId)
        )
}
