package com.diploma.controller

import com.diploma.dto.AddUser
import com.diploma.dto.AuthenticateDto
import com.diploma.dto.Logout
import com.diploma.exception.BadCredentials
import com.diploma.exception.UsernameNotFoundException
import com.diploma.security.jwt.JwtTokenProvider
import com.diploma.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import kotlin.jvm.Throws

@RestController
@RequestMapping("auth")
class AuthenticationController {
    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var jwtTokenProvider: JwtTokenProvider

    @GetMapping
    fun helloWorld(): String {
        return "Hello World"
    }

    @PostMapping("logout")
    fun logout(@RequestBody logout: Logout): ResponseEntity<Any> {
        if (jwtTokenProvider.validateToken(logout.token)) {
            SecurityContextHolder.getContext().authentication = null
            println(SecurityContextHolder.getContext().authentication)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build()
    }

    @PostMapping("login")
    fun login(@RequestBody autheticateDto: AuthenticateDto): ResponseEntity<MutableMap<String, Any>> {
        try {
            var username: String = autheticateDto.username
            var roles: Set<String> = emptySet()
            val token = userService.findByEmailOrMobile(username)
                .takeIf { it != null }?.let {
                    authenticationManager.authenticate(
                        UsernamePasswordAuthenticationToken(
                            it.email,
                            autheticateDto.password
                        )
                    )
                    username = it.email
                    roles = it.roles.map { it ->
                        it.name
                    }.toSet()
                    jwtTokenProvider.createToken(username, it.roles)
                } ?: run {
                throw UsernameNotFoundException("Неверный email или телефон")
            }
            val response: MutableMap<String, Any> = mutableMapOf()
            response["username"] = username
            response["token"] = token
            response["roles"] = roles
            return ResponseEntity.ok(response)
        } catch (ex: AuthenticationException) {
            throw BadCredentials("Неверный логин или пароль")
        }
    }

    @PostMapping("registration")
    fun register(@RequestBody addUser: AddUser) {
        print(addUser)
    }
}