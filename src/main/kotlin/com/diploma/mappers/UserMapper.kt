package com.diploma.mappers

import com.diploma.dto.Role
import com.diploma.dto.User
import com.diploma.entity.UserEntity
import org.springframework.stereotype.Service

@Service
class UserMapper {
    fun toResponse(user: UserEntity): User {
        return User(
            id = user.id,
            email = user.email,
            image = user.image,
            mobile = user.mobile,
            role = user.roles.map {
                Role(it.name)
            }
        )
    }
}