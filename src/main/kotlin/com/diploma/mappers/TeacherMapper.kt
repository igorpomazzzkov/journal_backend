package com.diploma.mappers

import com.diploma.dto.Teacher
import com.diploma.entity.TeacherEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TeacherMapper {

    @Autowired
    private lateinit var accountMapper: AccountMapper

    fun toResponse(teacherEntity: TeacherEntity): Teacher {
        return Teacher(
            id = teacherEntity.id,
            account = teacherEntity.account?.let {
                accountMapper.toResponse(it)
            },
            description = teacherEntity.description
        )
    }
}