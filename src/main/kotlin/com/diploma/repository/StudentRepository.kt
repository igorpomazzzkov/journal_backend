package com.diploma.repository

import com.diploma.entity.StudentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface StudentRepository : JpaRepository<StudentEntity, Long> {
    @Query("SELECT s FROM StudentEntity s WHERE s.lastName LIKE :name% OR s.firstName LIKE :name% OR s.middleName LIKE :name%")
    fun findStudentEntityByCustomQuery(name: String): List<StudentEntity>?
}