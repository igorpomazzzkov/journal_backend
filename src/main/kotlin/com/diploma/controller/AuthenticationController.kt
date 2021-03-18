package com.diploma.controller

import com.diploma.dto.AuthenticateDto
import com.diploma.dto.Logout
import com.diploma.dto.Role
import com.diploma.entity.UserEntity
import com.diploma.security.jwt.JwtTokenProvider
import com.diploma.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
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

    @Autowired
    private lateinit var passwordEncoder: BCryptPasswordEncoder

    @GetMapping
    fun helloWorld(): String {
        return "Hello World"
    }

    @PostMapping("logout")
    fun logout(@RequestBody logout: Logout): ResponseEntity<Any> {
        if (jwtTokenProvider.validateToken(logout.token)) {
            SecurityContextHolder.getContext().authentication = null
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build()
    }

    @PostMapping("login")
    @Throws(Exception::class)
    fun login(@RequestBody autheticateDto: AuthenticateDto): ResponseEntity<MutableMap<String, Any>> {
        try {
            var username: String = autheticateDto.username
            var roles: Set<String> = emptySet()
            val token = userService.findByEmailOrUsername(username)
                .takeIf { it != null }?.let {
                    authenticationManager.authenticate(
                        UsernamePasswordAuthenticationToken(
                            it.username,
                            autheticateDto.password
                        )
                    )
                    username = it.username
                    roles = it.roles.map { it ->
                        it.name
                    }.toSet()
                    jwtTokenProvider.createToken(username, it.roles)
                } ?: run {
                throw UsernameNotFoundException("User not found with username or email")
            }
            val response: MutableMap<String, Any> = mutableMapOf()
            response["username"] = username
            response["token"] = token
            response["roles"] = roles
            return ResponseEntity.ok(response)
        } catch (ex: AuthenticationException) {
            throw BadCredentialsException("Invalid username or password")
        }
    }
}