package com.diploma.exception

import java.sql.Timestamp

data class ErrorMessage(
    val status: Int,
    val message: String,
    val date: Timestamp,
    val description: String
)