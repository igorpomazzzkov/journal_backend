package com.diploma.dto

data class Student(
    val id: Long,
    val lastName: String?,
    val firstName: String?,
    val middleName: String?,
    val identifier: String?,
    val email: String?,
    val image: String?
)

data class UpdateStudent(
    val lastName: String?,
    val firstName: String?,
    val middleName: String?,
    val identifier: String?,
    val groupId: Long?,
    val email: String?,
    val password: String?,
    val roles: Set<Role>?,
    val image: String?,
    val mobile: String?
)