package com.diploma.mappers

import com.diploma.dto.AddUser
import com.diploma.dto.Role
import com.diploma.dto.User
import com.diploma.entity.RoleEntity
import com.diploma.entity.UserEntity
import org.springframework.stereotype.Service

@Service
class UserMapper {
    fun toResponse(user: UserEntity): User {
        return User(
            id = user.id,
            username = user.username,
            email = user.email,
            role = user.roles.map {
                Role(it.name)
            }
        )
    }

    fun toEntity(addUser: AddUser): UserEntity {
        return UserEntity(
            username = addUser.username,
            email = addUser.email,
            password = addUser.password,
            roles = addUser.roles.map {
                RoleEntity(name = it.name)
            }.toSet()
        )
    }
}