package com.diploma.service

import com.diploma.dto.AddJournal
import com.diploma.dto.AddJournalInfo
import com.diploma.dto.Journal
import com.diploma.entity.JournalEntity
import com.diploma.entity.JournalInfoEntity
import com.diploma.exception.JournalIdNotFoundedException
import com.diploma.mappers.JournalInfoMapper
import com.diploma.mappers.JournalMapper
import com.diploma.repository.JournalInfoRepository
import com.diploma.repository.JournalRepository
import com.diploma.telegram.TelegramService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*


@Service
class JournalService {
    @Autowired
    private lateinit var journalRepository: JournalRepository

    @Autowired
    private lateinit var journalInfoRepository: JournalInfoRepository

    @Autowired
    private lateinit var journalMapper: JournalMapper

    @Autowired
    private lateinit var journalInfoMapper: JournalInfoMapper

    @Autowired
    private lateinit var studentService: StudentService

    @Autowired
    private lateinit var telegramService: TelegramService

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

    fun getJournalInfo(id: Long): MutableList<JournalInfoEntity> {
        return journalInfoRepository.findJournalInfoEntitiesByJournalId(id)
    }

    fun addNewDateForJournal(id: Long, addJournalInfo: AddJournalInfo): JournalInfoEntity {
        val entity = journalInfoMapper.toEntity(addJournalInfo)
        telegramService.sendMessage(addJournalInfo.studentId, entity)
        return this.journalInfoRepository.save(journalInfoMapper.toEntity(addJournalInfo))
    }

    fun getHeader(id: Long): List<String> {
        return this.getJournalInfo(id).map {
            SimpleDateFormat("yyyy-MM-dd").format(it.dateMarks)
        }.sortedBy { it }.distinctBy { it }
    }

    fun getCell(id: Long): MutableList<MutableList<String>> {
        val items = HashMap<String, List<Int?>>()
        val journalInfo = this.getJournalInfo(id)
        val students = this.studentService.getAllStudentsByJournalId(id)
        val header = this.getHeader(id)
        for (student in students) {
            journalInfo.filter { it.student?.id == student.id }.let { marks ->
                var headerMarks =
                    header.map { h -> marks.find { SimpleDateFormat("yyyy-MM-dd").format(it.dateMarks) == h } }
                items.put(
                    "${student.account?.lastName} ${
                        student.account?.firstName?.substring(
                            0,
                            1
                        )
                    }. ${student.account?.middleName?.substring(0, 1)}.",
                    headerMarks.map { it?.mark }
                )
            }
        }
        return toCell(items)
    }

    private fun toCell(maps: HashMap<String, List<Int?>>): MutableList<MutableList<String>> {
        val res: MutableList<MutableList<String>> = mutableListOf()
        maps.keys.sortedBy { it }.forEach {
            val arr = mutableListOf<String>()
            arr.add(it)
            maps[it]?.forEach {
                if (it == null) {
                    arr.add("")
                } else {
                    arr.add(it.toString())
                }
            }
            res.add(arr)
        }
        println(res)
        return res
    }
}
