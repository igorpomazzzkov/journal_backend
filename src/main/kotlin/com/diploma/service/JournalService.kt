package com.diploma.service

import com.diploma.dto.AddJournal
import com.diploma.dto.Journal
import com.diploma.entity.JournalEntity
import com.diploma.entity.JournalInfoEntity
import com.diploma.exception.JournalIdNotFoundedException
import com.diploma.mappers.JournalMapper
import com.diploma.repository.JournalInfoRepository
import com.diploma.repository.JournalRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.util.*

@Service
class JournalService {
    @Autowired
    private lateinit var journalRepository: JournalRepository

    @Autowired
    private lateinit var journalInfoRepository: JournalInfoRepository

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
        val idNewJournal: Long = JournalEntity(
            groupId = addJournal.groupId,
            teacherId = addJournal.teacherId,
            subjectId = addJournal.subjectId,
            createdDate = Timestamp(Date().time),
            lastUpdated = Timestamp(Date().time)
        ).let {
            journalRepository.save(it).id
        }
        return this.journalRepository.findById(idNewJournal)
            .orElseThrow {
                throw JournalIdNotFoundedException("Журнал не найден")
            }
            .let {
                journalMapper.toResponse(it)
            }
    }

    fun getJournalById(id: Long): Journal {
        return this.journalRepository.findById(id).orElseThrow {
            throw JournalIdNotFoundedException("Журнал не найден")
        }.let {
            journalMapper.toResponse(it)
        }
    }

    fun getJournalInfo(id: Long): List<JournalInfoEntity>{
        return journalInfoRepository.findJournalInfoEntitiesByJournalId(id)
    }
}