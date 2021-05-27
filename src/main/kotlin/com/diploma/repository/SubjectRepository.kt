package com.diploma.repository

import com.diploma.entity.SubjectEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SubjectRepository : JpaRepository<SubjectEntity, Long> {
    fun findAllByNameIsContaining(name: String): List<SubjectEntity>
}
