package com.diploma.repository

import com.diploma.entity.StudentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StudentRepository : JpaRepository<StudentEntity, Long> {
    fun findAllByGroupId(groupId: Long): List<StudentEntity>
    fun findAllByAccountId(accountId: Long): List<StudentEntity>
    fun findByIdentifier(identifier: String): StudentEntity?
}
