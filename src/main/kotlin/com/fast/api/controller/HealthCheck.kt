package com.fast.api.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Endpoint to ping that the server is ok
 */
@RestController
@RequestMapping("/api/v1/health")
class HealthCheck : MainController() {

    @GetMapping
    fun basicHealthCheck(): String = "OK"
}