package com.diploma.security.jwt

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.Date

class JwtUser(
    private val id: Long? = null,
    private val username: String,
    private val password: String?,
    private val enabled: Boolean = true,
    private val lastPasswordResetDate: Date? = null,
    private val authorities: MutableCollection<out GrantedAuthority>?
) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority>? {
        return authorities
    }

    override fun getPassword(): String? {
        return password
    }

    override fun getUsername(): String {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return enabled
    }
}