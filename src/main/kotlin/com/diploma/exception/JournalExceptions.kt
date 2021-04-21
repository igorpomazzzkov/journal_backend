package com.diploma.exception

import org.springframework.http.HttpStatus

class JournalIdNotFoundedException(msg: String) : CustomException(HttpStatus.BAD_REQUEST, msg)