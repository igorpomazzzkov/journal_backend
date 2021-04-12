package com.diploma.mappers

import com.diploma.dto.Student
import com.diploma.entity.StudentEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class StudentMapper {

    @Autowired
    private lateinit var accountMapper: AccountMapper

    @Autowired
    private lateinit var groupMapper: GroupMapper

    fun toResponse(studentEntity: StudentEntity): Student {
        return Student(
            id = studentEntity.id,
            account = studentEntity.account?.let { accountMapper.toResponse(it) },
            identifier = studentEntity.identifier,
            group = studentEntity.group?.let { groupMapper.toResponse(it) }
        )
    }
}