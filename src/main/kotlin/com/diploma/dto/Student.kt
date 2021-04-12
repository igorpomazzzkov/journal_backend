package com.diploma.dto

data class Student(
    val id: Long,
    val account: Account?,
    val identifier: String?,
    val group: Group?,
)

data class AddStudent(
    val account: AddAccount?,
    val identifier: String?,
    val groupId: Long
)