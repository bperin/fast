package com.fast.api.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Controller for fetching movie details from
 */
@RestController
@RequestMapping("/api/v1/health")
class ViewerController {

    @GetMapping
    fun basicHealthCheck(): String = "OK"
}