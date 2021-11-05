package com.fast.api.controller

import com.fast.api.model.User
import com.fast.api.service.UsersService
import com.fast.api.util.UserUtil
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Controller for owners for CRUD operations on cinemas
 */
@RestController
@RequestMapping("/api/v1/users")
class UserController {

    @Autowired
    private lateinit var usersService: UsersService

    /**
     * Gets currently logged in user
     *
     */
    @ApiOperation("Returns the currently logged in user based off of saved user from JWT")
    @GetMapping("/me")
    fun getMe(): User? {
        return UserUtil.user
    }

    @ApiOperation("returns the owner given their id")
    @GetMapping("/owners/{id}")
    fun getOwner(@PathVariable id: String): User? {
        return usersService.getOwner(id)
    }

    @ApiOperation("returns the viewer given their id")
    @GetMapping("/viewers/{id}")
    fun getViewer(@PathVariable id: String): User? {
        return usersService.getViewer(id)
    }
}