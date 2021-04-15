package com.diploma.dto

import java.sql.Timestamp

data class Journal(
    val id: Long?,
    val group: Group?,
    val subject: Subject?,
    val teacher: Teacher?,
    val createdDate: Timestamp?,
    val lastUpdated: String?,
    val teacherFio: String?,
)


data class AddJournal(
    val groupId: Long,
    val subjectId: Long,
    val teacherId: Long,
)