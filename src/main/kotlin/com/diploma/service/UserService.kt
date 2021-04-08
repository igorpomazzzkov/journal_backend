package com.diploma.service

import com.diploma.dto.Account
import com.diploma.entity.UserEntity
import com.diploma.mappers.AccountMapper
import com.diploma.repository.AccountRepository
import com.diploma.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

interface UserService {
    fun getAllUsers(): List<Account>
    fun findByEmail(email: String): Account?
    fun findById(id: Set<Long>): List<Account>?
    fun findByEmailOrMobile(email: String): UserEntity?
    fun delete(id: Long)
}

@Service
class UserServiceImpl : UserService {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var accountRepository: AccountRepository

    @Autowired
    private lateinit var accountMapper: AccountMapper

    override fun getAllUsers(): List<Account> {
        return this.accountRepository.findAll().map {
            accountMapper.toResponse(it)
        }
    }

    override fun findByEmail(email: String): Account? {
        return this.accountRepository.findAccountEntityByEmail(email)?.let {
            accountMapper.toResponse(it)
        }
    }

    override fun findById(ids: Set<Long>): List<Account>? {
        return this.accountRepository.findAllById(ids).map {
            accountMapper.toResponse(it)
        }
    }

    override fun findByEmailOrMobile(username: String): UserEntity? {
        return this.userRepository.findByEmailOrMobile(username, username)
    }

    override fun delete(id: Long) {
        this.userRepository.deleteById(id)
    }
}