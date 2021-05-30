package com.diploma.mappers

import com.diploma.dto.AddJournalInfo
import com.diploma.dto.Journal
import com.diploma.dto.JournalInfo
import com.diploma.entity.JournalEntity
import com.diploma.entity.JournalInfoEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.Timestamp

@Service
class JournalMapper {
    @Autowired
    private lateinit var groupMapper: GroupMapper

    @Autowired
    private lateinit var subjectMapper: SubjectMapper

    @Autowired
    private lateinit var accountMapper: AccountMapper

    fun toResponse(journalEntity: JournalEntity): Journal {
        val teacherFio = journalEntity.account?.lastName + " " +
                journalEntity.account?.firstName?.substring(0, 1)?.toUpperCase() + ". " +
                journalEntity.account?.middleName?.substring(0, 1)?.toUpperCase() + "."
        return Journal(
            id = journalEntity.id,
            teacher = journalEntity.account?.let { accountMapper.toResponse(it) },
            subject = journalEntity.subject?.let { subjectMapper.toResponse(it) },
            group = journalEntity.group?.let { groupMapper.toResponse(it) },
            createdDate = journalEntity.createdDate,
            lastUpdated = journalEntity.lastUpdated.toString().substring(0, 19),
            teacherFio = teacherFio
        )
    }
}

@Service
class JournalInfoMapper {

    @Autowired
    private lateinit var studentMapper: StudentMapper

    fun toResponse(journalInfoEntity: JournalInfoEntity): JournalInfo {
        return JournalInfo(
            student = journalInfoEntity.student?.let { studentMapper.toResponse(it) },
            mark = journalInfoEntity.mark,
            markType = journalInfoEntity.markType,
            date = journalInfoEntity.date,
        )
    }

    fun toEntity(addJournalInfo: AddJournalInfo): JournalInfoEntity {
        return JournalInfoEntity(
            studentId = addJournalInfo.studentId,
            journalId = addJournalInfo.journalId,
            markType = addJournalInfo.markType,
            description = addJournalInfo.desc,
            date = Timestamp(addJournalInfo.date.toLong())
        )
    }
}