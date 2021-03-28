package com.diploma.mappers

import com.diploma.dto.Teacher
import com.diploma.entity.TeacherEntity
import org.springframework.stereotype.Service

@Service
class TeacherMapper {
    fun toResponse(teacherEntity: TeacherEntity): Teacher {
        return Teacher(
            id = teacherEntity.id,
            lastName = teacherEntity.lastName,
            firstName = teacherEntity.firstName,
            middleName = teacherEntity.middleName,
            email = teacherEntity.user?.email,
            image = teacherEntity.user?.image,
            description = teacherEntity.description
        )
    }
}