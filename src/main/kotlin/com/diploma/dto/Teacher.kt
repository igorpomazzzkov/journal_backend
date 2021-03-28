package com.diploma.dto

data class Teacher(
    val id: Long,
    val firstName: String?,
    val lastName: String?,
    val middleName: String?,
    val description: String?,
    val email: String?,
    val image: String?,
)

data class AddTeacher(
    val firstName: String,
    val lastName: String,
    val middleName: String,
    val description: String?,
    val addUser: AddUser?
)

data class UpdateTeacher(
    val firstName: String,
    val lastName: String,
    val middleName: String,
    val description: String?,
    val email: String?,
    val password: String?,
    val roles: Set<Role>?,
    val image: String?,
    val mobile: String?
)