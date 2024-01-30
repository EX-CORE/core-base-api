package com.core.base.corebase

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CoreBaseApplication

fun main(args: Array<String>) {
	runApplication<CoreBaseApplication>(*args)
}
