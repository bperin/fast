package com.fast.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FastApplication

fun main(args: Array<String>) {
	runApplication<FastApplication>(*args)
}
