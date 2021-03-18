package com.diploma.security.jwt

import com.diploma.exception.ErrorMessage
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import java.sql.Timestamp
import java.util.*

class JwtAuthenticationException(msg: String) : AuthenticationException(msg)

@ControllerAdvice
class JwtExceptionHandler() {
    @ExceptionHandler(JwtAuthenticationException::class)
    fun jwtExceptionHandler(
        ex: JwtAuthenticationException,
        request: WebRequest
    ): ResponseEntity<ErrorMessage> {
        val errorMessage = ErrorMessage(
            401,
            ex.message!!.substring(4),
            Timestamp(Date().time),
            request.getDescription(false)
        )
        return ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.UNAUTHORIZED)
    }
}