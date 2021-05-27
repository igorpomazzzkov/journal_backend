package com.diploma.service

import com.diploma.dto.Account
import com.diploma.dto.Group
import com.diploma.entity.SubjectEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SearchService {

    @Autowired
    private lateinit var subjectService: SubjectService

    @Autowired
    private lateinit var studentService: StudentService

    @Autowired
    private lateinit var teacherService: TeacherService

    fun search(nameDesc: String): List<SearchResponse> {
        val response = mutableListOf<SearchResponse>()
        val subjects: List<SubjectEntity> = this.subjectService.searchSubject(nameDesc)
        val students: List<Account> = this.studentService.searchStudent(nameDesc)
        val teachers: List<Account> = this.teacherService.searchTeacher(nameDesc)
        response.addAll(subjects.map {
            SearchResponse(it.shortName, it.name, EntityProject.SUBJECT, it.id)
        })
        response.addAll(students.map {
            SearchResponse(
                "${it.lastName} ${it.firstName?.substring(0, 1)} ${it.middleName?.substring(0, 1)}",
                "${it.lastName} ${it.firstName} ${it.middleName}",
                EntityProject.STUDENT,
                it.id
            )
        })
        response.addAll(teachers.map {
            SearchResponse(
                "${it.lastName} ${it.firstName?.substring(0, 1)} ${it.middleName?.substring(0, 1)}",
                "${it.lastName} ${it.firstName} ${it.middleName}",
                EntityProject.TEACHER,
                it.id
            )
        })
        return response.distinctBy { it.name }
    }
}

data class SearchResponse(
    val name: String?,
    val description: String?,
    val entity: EntityProject,
    val id: Long
)

enum class EntityProject {
    GROUP, SUBJECT, STUDENT, TEACHER, JOURNAL
}
