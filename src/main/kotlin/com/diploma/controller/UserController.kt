package com.diploma.controller

import com.diploma.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("user")
class UserController {

    @Autowired
    private lateinit var userService: UserService

    @GetMapping("active")
    fun getActiveUser(): String {
        return "Hi, user"
    }
}