package com.diploma.repository

import com.diploma.entity.AccountEntity
import com.diploma.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByEmail(email: String): UserEntity?
    fun findByEmailOrMobile(email: String, mobile: String): UserEntity?
}

interface AccountRepository: JpaRepository<AccountEntity, Long>{
    @Query(value = "SELECT a FROM AccountEntity a WHERE a.user.email = :email")
    fun findAccountEntityByEmail(email: String): AccountEntity?
}