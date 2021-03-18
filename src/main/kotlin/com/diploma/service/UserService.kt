package com.diploma.service

import com.diploma.dto.AddUser
import com.diploma.dto.User
import com.diploma.entity.RoleEntity
import com.diploma.entity.UserEntity
import com.diploma.mappers.UserMapper
import com.diploma.repository.RoleRepository
import com.diploma.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service


interface UserService {
    fun addUser(addUser: AddUser): User
    fun getAllUsers(): List<User>
    fun findByUsername(username: String): User?
    fun findByEmail(username: String): User?
    fun findById(id: Set<Long>): List<User>?
    fun findByEmailOrUsername(username: String?): UserEntity?
    fun delete(id: Long)
}

@Service
class UserServiceImpl : UserService {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var roleRepository: RoleRepository

    @Autowired
    private lateinit var passwordEncoder: BCryptPasswordEncoder

    @Autowired
    private lateinit var userMapper: UserMapper

    override fun addUser(addUser: AddUser): User {
        val userToDB = userMapper.toEntity(addUser)
        if (userToDB.roles.isEmpty()) {
            val teacherRole = this.roleRepository.findByName("TEACHER")
            val roles: List<RoleEntity> = emptyList()
            roles.plus(teacherRole)
        }
        userToDB.password = passwordEncoder.encode(userToDB.password)
        return this.userRepository.save(userToDB).let {
            userMapper.toResponse(it)
        }
    }

    override fun getAllUsers(): List<User> {
        return this.userRepository.findAll().map {
            userMapper.toResponse(it)
        }
    }

    override fun findByUsername(username: String): User? {
        return this.userRepository.findByUsername(username)?.let {
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

    override fun findByEmailOrUsername(username: String?): UserEntity? {
        return this.userRepository.findByEmailOrUsername(username)
    }

    override fun delete(id: Long) {
        this.userRepository.deleteById(id)
    }
}