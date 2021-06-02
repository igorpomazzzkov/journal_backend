package com.diploma.dto

import java.sql.Timestamp
import java.util.*

data class Journal(
    val id: Long?,
    val group: Group?,
    val subject: Subject?,
    val teacher: Account?,
    val createdDate: Timestamp?,
    val lastUpdated: String?,
    val teacherFio: String?,
)


data class AddJournal(
    val groupId: Long,
    val subjectId: Long,
    val teacherId: Long,
)

data class JournalInfo(
    val student: Student?,
    val mark: Int?,
    val markType: String?,
    val date: String?
)

data class AddJournalInfo(
    val studentId: Long,
    val journalId: Long,
    val mark: Int,
    val markType: String,
    val date: Long?,
    val desc: String?
)
