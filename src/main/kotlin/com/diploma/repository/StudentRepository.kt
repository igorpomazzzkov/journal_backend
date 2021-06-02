package com.diploma.repository

import com.diploma.entity.StudentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface StudentRepository : JpaRepository<StudentEntity, Long> {
    fun findAllByGroupId(groupId: Long): List<StudentEntity>
    fun findAllByAccountId(accountId: Long): List<StudentEntity>
    fun findByIdentifier(identifier: String): StudentEntity?

    @Query("SELECT j.group.students FROM JournalEntity j WHERE j.id = :journalId")
    fun findAllByJournalId(journalId: Long): List<StudentEntity>
}
