package com.diploma.security.jwt

import com.diploma.entity.Role
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct
import javax.servlet.http.HttpServletRequest

@Component
class JwtTokenProvider {

    @Value(value = "\${jwt.token.secret}")
    private lateinit var secret: String

    @Value(value = "\${jwt.token.expired}")
    private lateinit var expired: String

    @Qualifier("jwtUserDetailsService")
    @Autowired
    private lateinit var userDetailsService: UserDetailsService

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder(12)
    }

    @PostConstruct
    protected fun init() {
        secret = Base64.getEncoder().encodeToString(secret.toByteArray())
    }

    fun createToken(username: String?, roles: Set<Role>): String {
        val claims: Claims = Jwts.claims().setSubject(username)
        claims["roles"] = getRoleNames(roles)
        claims["username"] = username
        val now = Date()
        val dateValidate = Date(now.time + expired.toLong())

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(dateValidate)
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact()
    }

    fun getAuthentication(token: String): Authentication {
        val userDetails: UserDetails = userDetailsService.loadUserByUsername(getUsernameByToken(token))
        return UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
    }

    fun getUsernameByToken(token: String): String {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body.subject
    }

    fun resolveToken(request: HttpServletRequest): String? {
        val bearer: String? = request.getHeader("Authorization")
        if (!bearer.isNullOrEmpty() && bearer.startsWith("Bearer ")) {
            return bearer.substring(7, bearer.length)
        }
        return null
    }

    @Throws(AuthenticationException::class)
    fun validateToken(token: String): Boolean {
        try{
            val claims: Jws<Claims> = Jwts.parser().setSigningKey(secret).parseClaimsJws(token)
            if (claims.body.expiration.before(Date())) {
                return false
            }
            return true
        } catch (ex: JwtException){
            return false
        }

    }

    private fun getRoleNames(roles: Set<Role>): Set<String> {
        return roles.map {
            it.name
        }.toSet()
    }
}