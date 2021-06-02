package com.diploma.repository

import com.diploma.entity.JournalEntity
import com.diploma.entity.JournalInfoEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface JournalRepository: JpaRepository<JournalEntity, Long> {
    fun findAllByTeacherId(teacherId: Long): List<JournalEntity>?
    fun findAllByGroupId(groupId: Long): List<JournalEntity>?
}

@Repository
interface JournalInfoRepository: JpaRepository<JournalInfoEntity, Long>{
    fun findJournalInfoEntitiesByJournalId(journalId: Long): MutableList<JournalInfoEntity>
}