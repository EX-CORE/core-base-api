package com.core.base.corebase.integration

import com.core.base.corebase.base.IntegrationTestSpec
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class BaseApiTest(
	val mockMvc: MockMvc
): IntegrationTestSpec({

	Given("Server running") {

		When("Request /health-check") {
			val res = mockMvc.perform(get("/health-check")).andDo(print())

			Then("Request 200 status") {
				res.andExpect(status().isOk())
			}
		}
	}

})
