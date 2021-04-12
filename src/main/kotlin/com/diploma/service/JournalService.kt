package com.diploma.service

import com.diploma.dto.AddJournal
import com.diploma.dto.Journal
import com.diploma.entity.JournalEntity
import com.diploma.mappers.JournalMapper
import com.diploma.repository.JournalRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.util.Date

@Service
class JournalService {
    @Autowired
    private lateinit var journalRepository: JournalRepository

    @Autowired
    private lateinit var journalMapper: JournalMapper

    fun getAllJournals() = this.journalRepository.findAll().map {
        journalMapper.toResponse(it)
    }

    fun getJournalsByTeacherId(teacherId: Long) = this.journalRepository.findAllByTeacherId(teacherId)?.map {
        journalMapper.toResponse(it)
    }

    fun getJournalsByGroupId(groupId: Long) = this.journalRepository.findAllByGroupId(groupId)?.map {
        journalMapper.toResponse(it)
    }

    fun deleteById(id: Long) {
        this.journalRepository.deleteById(id)
    }

    fun deleteByIds(ids: Set<Long>) {
        this.journalRepository.deleteAllById(ids)
    }

    fun addNewJournal(addJournal: AddJournal): Journal {
        return JournalEntity(
            groupId = addJournal.groupId,
            teacherId = addJournal.teacherId,
            subjectId = addJournal.subjectId,
            createdDate = Timestamp(Date().time),
            lastUpdated = Timestamp(Date().time)
        ).let {
            journalRepository.save(it).let { journalMapper.toResponse(it) }
        }
    }
}