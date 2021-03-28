package com.diploma.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse

@RestController
class MainController {
    @GetMapping
    fun main(httpServletResponse: HttpServletResponse) {
        httpServletResponse.setHeader("Location", "swagger-ui.html")
        httpServletResponse.status = 302
    }
}