package com.diploma.repository

import com.diploma.entity.TeacherEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TeacherRepository : JpaRepository<TeacherEntity, Long> {
    @Query("SELECT s FROM TeacherEntity s WHERE s.lastName LIKE :name% OR s.firstName LIKE :name% OR s.middleName LIKE :name%")
    fun findTeachersByCustomQuery(name: String): List<TeacherEntity>?
}