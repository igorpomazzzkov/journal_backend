package com.diploma.mappers

import com.diploma.dto.Journal
import com.diploma.entity.JournalEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class JournalMapper {
    @Autowired
    private lateinit var groupMapper: GroupMapper

    @Autowired
    private lateinit var subjectMapper: SubjectMapper

    @Autowired
    private lateinit var teacherMapper: TeacherMapper

    fun toResponse(journalEntity: JournalEntity): Journal {
        val teacherFio = journalEntity.teacherEntity?.account?.lastName + " " +
                journalEntity.teacherEntity?.account?.firstName?.substring(0, 1)?.toUpperCase() + ". " +
                journalEntity.teacherEntity?.account?.middleName?.substring(0, 1)?.toUpperCase() + "."
        return Journal(
            id = journalEntity.id,
            teacher = journalEntity.teacherEntity?.let { teacherMapper.toResponse(it) },
            subject = journalEntity.subject?.let { subjectMapper.toResponse(it) },
            group = journalEntity.group?.let { groupMapper.toResponse(it) },
            createdDate = journalEntity.createdDate,
            lastUpdated = journalEntity.lastUpdated.toString().substring(0, 19),
            teacherFio = teacherFio
        )
    }
}