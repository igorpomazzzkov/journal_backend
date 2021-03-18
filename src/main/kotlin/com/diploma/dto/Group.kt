package com.diploma.dto

data class Group(
    val id: Long,
    val name: String,
    val countOfStudents: Int?,
    val students: List<Student>?,
    val course: Int?
)