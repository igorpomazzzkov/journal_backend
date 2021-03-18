package com.diploma.mappers

import com.diploma.dto.Group
import com.diploma.entity.GroupEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GroupMapper {

    @Autowired
    private lateinit var studentMapper: StudentMapper

    fun toResponse(groupEntity: GroupEntity): Group {
        return Group(
            id = groupEntity.id,
            name = groupEntity.name,
            countOfStudents = groupEntity.students.size,
            course = groupEntity.course,
            students = groupEntity.students.map {
                studentMapper.toResponse(it)
            }
        )
    }
}