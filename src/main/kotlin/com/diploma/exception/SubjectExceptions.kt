package com.diploma.exception

import org.springframework.http.HttpStatus

class SubjectIdNotFoundedException(msg: String) : CustomException(HttpStatus.BAD_REQUEST, msg)