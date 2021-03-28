package com.diploma.exception

import org.springframework.http.HttpStatus

class GroupIdNotFoundedException(msg: String) : CustomException(HttpStatus.BAD_REQUEST, msg)