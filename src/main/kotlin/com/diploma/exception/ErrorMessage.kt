package com.diploma.exception

import org.springframework.http.HttpStatus
import java.sql.Timestamp

data class ErrorMessage(
    val status: Int,
    val message: String?,
    val date: Timestamp,
    val description: String?
)

open class CustomException(val status: HttpStatus, val msg: String) : Exception()