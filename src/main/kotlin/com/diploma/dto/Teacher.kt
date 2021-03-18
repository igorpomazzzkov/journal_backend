package com.diploma.dto

data class Teacher(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val middleName: String
)

data class AddTeacher(
    val firstName: String,
    val lastName: String,
    val middleName: String,
    val types: Set<AddTeacherType>?
)

data class TeacherType(
    val id: Long,
    val name: String
)

data class AddTeacherType(
    val name: String
)