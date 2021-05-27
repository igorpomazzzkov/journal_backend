package com.diploma.repository

import com.diploma.entity.TeacherEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TeacherRepository: JpaRepository<TeacherEntity, Long>{
    fun findAllByAccountId(accountId: Long): List<TeacherEntity>
}
