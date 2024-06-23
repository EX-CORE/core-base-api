package com.core.base.corebase.integration

import io.kotest.core.spec.style.BehaviorSpec
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class BaseApiTest(
	val mockMvc: MockMvc
): BehaviorSpec({

	Given("Server running") {
		When("Request /health-check") {
			val res = mockMvc.perform(get("/health-check")).andDo(print())
			Then("Request 200 status") {
				res.andExpect(status().isOk())
			}
		}
	}

})
