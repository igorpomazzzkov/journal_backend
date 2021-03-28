package com.diploma.service

import com.diploma.dto.User
import com.diploma.entity.UserEntity
import com.diploma.mappers.UserMapper
import com.diploma.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

interface UserService {
    fun getAllUsers(): List<User>
    fun findByEmail(email: String): User?
    fun findById(id: Set<Long>): List<User>?
    fun findByEmailOrMobile(email: String): UserEntity?
    fun delete(id: Long)
    fun authenticatedUser(): String?
}

@Service
class UserServiceImpl : UserService {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var passwordEncoder: BCryptPasswordEncoder

    @Autowired
    private lateinit var userMapper: UserMapper

    override fun getAllUsers(): List<User> {
        return this.userRepository.findAll().map {
            userMapper.toResponse(it)
        }
    }

    override fun findByEmail(email: String): User? {
        return this.userRepository.findByEmail(email)?.let {
            userMapper.toResponse(it)
        }
    }

    override fun findById(ids: Set<Long>): List<User>? {
        return this.userRepository.findAllById(ids).map {
            userMapper.toResponse(it)
        }
    }

    override fun findByEmailOrMobile(username: String): UserEntity? {
        return this.userRepository.findByEmailOrMobile(username, username)
    }

    override fun delete(id: Long) {
        this.userRepository.deleteById(id)
    }

    override fun authenticatedUser(): String? {
        return SecurityContextHolder.getContext().authentication.name
    }
}