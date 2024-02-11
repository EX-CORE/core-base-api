package com.core.base.corebase

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@SpringBootApplication
class CoreBaseApplication

fun main(args: Array<String>) {
	runApplication<CoreBaseApplication>(*args)
}
