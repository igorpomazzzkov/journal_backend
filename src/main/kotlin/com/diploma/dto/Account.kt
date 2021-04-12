package com.diploma.dto

import java.sql.Timestamp

data class Account(
    val id: Long,
    val firstName: String?,
    val lastName: String?,
    val middleName: String?,
    val birthday: Timestamp?,
    val address: String?,
    val image: String?,
)

data class AddAccount(
    val firstName: String?,
    val lastName: String?,
    val middleName: String?,
    val birthday: Timestamp?,
    val address: String?,
    val image: String?,
)

data class UpdateAccount(
    val firstName: String?,
    val lastName: String?,
    val middleName: String?,
    val birthday: Timestamp?,
    val address: String?,
    val image: String?,
)