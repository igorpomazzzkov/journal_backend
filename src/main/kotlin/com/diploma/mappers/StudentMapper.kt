package com.diploma.mappers

import com.diploma.dto.Student
import com.diploma.entity.StudentEntity
import org.springframework.stereotype.Service

@Service
class StudentMapper {
    fun toResponse(studentEntity: StudentEntity): Student {
        return Student(
            id = studentEntity.id,
            lastName = studentEntity.lastName,
            firstName = studentEntity.firstName,
            middleName = studentEntity.middleName,
            identifier = studentEntity.identifier
        )
    }
}