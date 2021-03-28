package com.diploma.security

import com.diploma.security.jwt.JwtUserMapper
import com.diploma.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class JwtUserDetailsService : UserDetailsService {

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var jwtUserMap: JwtUserMapper

    override fun loadUserByUsername(username: String): UserDetails {
        return this.userService.findByEmailOrMobile(username).takeIf { it != null }.let {
            it?.let { it1 -> jwtUserMap.createUserJwt(it1) }
        } ?: run {
            throw UsernameNotFoundException("User is not exists")
        }
    }
}