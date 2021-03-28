package com.diploma.exception

import org.springframework.http.HttpStatus

class UsernameNotFoundException(msg: String) : CustomException(HttpStatus.BAD_REQUEST, msg)
class BadCredentials(msg: String) : CustomException(HttpStatus.BAD_REQUEST, msg)