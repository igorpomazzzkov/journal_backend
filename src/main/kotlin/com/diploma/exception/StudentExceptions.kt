package com.diploma.exception

import org.springframework.http.HttpStatus

class StudentIdNotFoundedException(msg: String) : CustomException(HttpStatus.BAD_REQUEST, msg)