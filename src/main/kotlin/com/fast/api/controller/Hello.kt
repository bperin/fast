package com.fast.api.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Hello {

    @GetMapping("healthcheck")
    fun sayHello(): String {
        return "hello world we're up"
    }
}