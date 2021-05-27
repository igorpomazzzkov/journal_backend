package com.diploma.controller

import com.diploma.service.SearchService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse

@RestController
class MainController {
    @Autowired
    private lateinit var searchService: SearchService

    @GetMapping
    fun main(httpServletResponse: HttpServletResponse) {
        httpServletResponse.setHeader("Location", "swagger-ui.html")
        httpServletResponse.status = 302
    }

    @GetMapping(params = ["nameDesc"])
    fun mainSearch(@RequestParam nameDesc: String) = this.searchService.search(nameDesc)
}
