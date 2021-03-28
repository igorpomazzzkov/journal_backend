package com.diploma.dto

data class Role(
    val name: String
)

data class Logout(
    val token: String
)

data class User(
    val id: Long?,
    val email: String?,
    val role: List<Role>?,
    val image: String?,
    val mobile: String?
)

data class AddUser(
    val firstName: String,
    val lastName: String,
    val middleName: String,
    val email: String,
    val password: String,
    val groupId: Long?,
    val roles: Set<Role>?,
    val image: String?,
    val mobile: String
)

data class UpdateUser(
    val firstName: String?,
    val lastName: String?,
    val middleName: String?,
    val groupId: Long?,
    val email: String?,
    val password: String?,
    val roles: Set<Role>?,
    val image: String?,
    val mobile: String?
)

data class AuthenticateDto(
    val username: String,
    val password: String
)