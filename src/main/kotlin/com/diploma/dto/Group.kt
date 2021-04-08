package com.diploma.dto

data class Group(
    val id: Long,
    val name: String?,
    val countOfStudents: Int?,
//    val students: List<Student>?,
    val course: Int?
)

data class AuthGroup(
    val id: Long,
    val name: String?
)

data class AddGroup(
    val name: String?,
    val course: Int?,
)

data class UpdateGroup(
    val name: String?,
    val course: Int?,
)