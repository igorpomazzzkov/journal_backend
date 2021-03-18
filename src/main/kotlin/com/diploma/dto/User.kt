package com.diploma.dto

data class Role(
    val name: String
)

data class Logout(
    val token: String
)

data class User(
    val id: Long,
    val username: String?,
    val email: String?,
    val role: List<Role>?,
)

data class AddUser(
    val username: String,
    val email: String,
    val password: String,
    val roles: Set<Role>,
    val teacher: AddTeacher
)

data class AuthenticateDto(
    val username: String,
    val password: String
)