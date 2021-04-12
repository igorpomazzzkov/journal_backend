package com.diploma.service

import com.diploma.dto.AddStudent
import com.diploma.dto.Student
import com.diploma.entity.StudentEntity
import com.diploma.exception.StudentIdNotFoundedException
import com.diploma.mappers.AccountMapper
import com.diploma.mappers.StudentMapper
import com.diploma.repository.StudentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class StudentService {
    @Autowired
    private lateinit var repository: StudentRepository

    @Autowired
    private lateinit var mapper: StudentMapper

    @Autowired
    private lateinit var accountMapper: AccountMapper

    fun getAll(): List<Student> {
        return this.repository.findAll().map {
            mapper.toResponse(it)
        }
    }

    fun getAllStudents(pageable: Pageable): Page<Student> {
        return this.repository.findAll(pageable).map {
            mapper.toResponse(it)
        }
    }

    fun getAllStudentsByGroupId(groupId: Long) = this.repository.findAllByGroupId(groupId)?.map {
        mapper.toResponse(it)
    }

    fun getStudentById(id: Long): Student {
        return this.repository.findById(id).orElseThrow {
            throw StudentIdNotFoundedException("Студент не найден")
        }.let {
            mapper.toResponse(it)
        }
    }

    fun deleteById(id: Long) {
        this.repository.deleteById(id)
    }

    fun deleteByIds(ids: Set<Long>) {
        this.repository.deleteAllById(ids)
    }

    fun save(addStudent: AddStudent): Student {
        val studentEntity = StudentEntity(
            identifier = addStudent.identifier,
            groupId = addStudent.groupId,
            account = addStudent.account?.let { accountMapper.toEntity(it) }
        )
        return this.repository.save(studentEntity).let {
            mapper.toResponse(it)
        }
    }
}