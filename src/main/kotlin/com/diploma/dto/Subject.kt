package com.diploma.dto

data class Subject(
    val id: Long?,
    val name: String?,
    val shortName: String?
)

data class AddSubject(
    val name: String?,
    val shortName: String?
)

data class UpdateSubject(
    val name: String?,
    val shortName: String?
)