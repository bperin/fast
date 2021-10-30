package com.fast.api.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Controller for owners for CRUD operations on cinemas
 */
@RestController
@RequestMapping("/api/v1/owners")
class OwnerController {

    @GetMapping
    fun basicHealthCheck(): String = "OK"
}