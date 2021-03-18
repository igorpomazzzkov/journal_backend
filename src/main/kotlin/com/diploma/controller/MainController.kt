package com.diploma.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MainController {
    @GetMapping
    fun main(): String{
        return "Hello. There is diploma project of Igor Pomazkov"
    }
}