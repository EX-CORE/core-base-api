package com.core.base.corebase.support

import com.core.base.corebase.common.code.ErrorCode
import com.core.base.corebase.common.dto.BaseExceptionRes
import com.core.base.corebase.common.exception.BaseException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

class GlobalExceptionHandlerTest: BehaviorSpec({

    val objectMapper = ObjectMapper().registerKotlinModule()

    fun <T> getBaseTestMockMvc(controller: T): MockMvc {
        return MockMvcBuilders.standaloneSetup(controller)
            .setControllerAdvice(GlobalExceptionHandler())
            .build()
    }

    Given("Exists throw exception controller") {
        @RestController
        class TestController {
            @GetMapping(value = ["/test"])
            fun test() {
                throw RuntimeException("test message")
            }
        }

        When("Request test controller api") {
            val res = getBaseTestMockMvc(TestController())
                .perform(get("/test"))
                .andDo(print())

            Then("Response status should be 500") {
                res.andExpect { status().isInternalServerError }
            }

            Then("Response body is correct") {
                val result =
                    objectMapper.readValue(res.andReturn().response.contentAsString, BaseExceptionRes::class.java)
                result.status shouldBe HttpStatus.INTERNAL_SERVER_ERROR
                result.code shouldBe ErrorCode.INTERNAL_SERVER_ERROR
                result.reason shouldBe "test message"
                result.timeStamp.shouldNotBeNull()
            }
        }
    }

    Given("Exists throw base exception controller") {
        val baseException = BaseException(ErrorCode.INVALID_TOKEN, "test token")

        @RestController
        class TestController {
            @GetMapping(value = ["/test"])
            fun test() {
                throw baseException
            }
        }

        When("Request test controller api") {
            val res = getBaseTestMockMvc(TestController())
                .perform(get("/test"))
                .andDo(print())

            Then("Response status should be 401") {
                res.andExpect { status().isUnauthorized }
            }

            Then("Response body is correct") {
                val result =
                    objectMapper.readValue(res.andReturn().response.contentAsString, BaseExceptionRes::class.java)
                result.status shouldBe HttpStatus.UNAUTHORIZED
                result.code shouldBe ErrorCode.INVALID_TOKEN
                result.reason shouldBe baseException.reason
                result.timeStamp.shouldNotBeNull()
            }
        }
    }

})