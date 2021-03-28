package com.diploma.security.jwt

import com.diploma.exception.CustomException
import com.diploma.exception.ErrorMessage
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.sql.Timestamp
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.security.core.userdetails.UsernameNotFoundException


@Component
class JwtTokenFilter(
    @Autowired
    private val jwtTokenProvider: JwtTokenProvider
) : OncePerRequestFilter() {
    @Throws(Exception::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val token: String? = this.jwtTokenProvider.resolveToken(request)
            if (token != null) {
                jwtTokenProvider.validateToken(token).takeIf { it }?.apply {
                    val authentication: Authentication = jwtTokenProvider.getAuthentication(token)
//                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().authentication = authentication
                }
            }
            filterChain.doFilter(request, response)
        } catch (e: Exception) {
            if (e.cause is CustomException) {
                setErrorResponse(response, e.cause as CustomException)
            }
        }
    }

    fun setErrorResponse(response: HttpServletResponse, ex: CustomException) {
        response.contentType = "application/json"
        response.characterEncoding = "UTF-8"
        val error = ErrorMessage(ex.status.value(), ex.msg, Timestamp(Date().time), ex.message)
        val mapper = ObjectMapper()
        mapper.registerModule(JavaTimeModule())
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        response.status = error.status
        response.writer.write(mapper.writeValueAsString(error))
    }
}