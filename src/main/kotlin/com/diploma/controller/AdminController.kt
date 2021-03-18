package com.diploma.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("admin")
class AdminController {
    @GetMapping
    fun helloAdmin(): String {
        return "Hello Admin"
    }
}