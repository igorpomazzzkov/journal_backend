package com.diploma.dto

data class Teacher(
    val id: Long,
    val account: Account?,
    val description: String?
)

data class AddTeacher(
    val account: AddAccount,
    val description: String?
)