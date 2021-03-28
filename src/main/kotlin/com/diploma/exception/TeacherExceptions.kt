package com.diploma.exception

import org.springframework.http.HttpStatus

class TeacherIdNotFoundedException(msg: String) : CustomException(HttpStatus.BAD_REQUEST, msg)