package com.fast.api.controller


import com.google.gson.JsonObject
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/check")
class Hello {

    @GetMapping("/")
    fun sayHello() {
        return JsonObject().addProperty("good", "to go")
    }
}