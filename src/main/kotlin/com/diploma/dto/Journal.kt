package com.diploma.dto

import java.sql.Timestamp

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
    val date: Timestamp?
)