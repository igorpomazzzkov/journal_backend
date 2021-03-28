package com.diploma.security.jwt

import com.diploma.entity.Role
import com.diploma.entity.UserEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service

@Service
class JwtUserMapper {
    fun createUserJwt(userEntity: UserEntity): JwtUser {
        return JwtUser(
            id = userEntity.id,
            username = userEntity.email,
            password = userEntity.password,
            authorities = mapToGrantedAuthorities(userEntity.roles),
            enabled = true,
            lastPasswordResetDate = null
        )
    }

    private fun mapToGrantedAuthorities(roles: Set<Role>): MutableList<GrantedAuthority> {
        return roles.map { SimpleGrantedAuthority(it.name) }.toMutableList()
    }
}