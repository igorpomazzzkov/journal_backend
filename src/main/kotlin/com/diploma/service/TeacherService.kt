package com.diploma.service

import com.diploma.dto.AddTeacher
import com.diploma.dto.Teacher
import com.diploma.entity.TeacherEntity
import com.diploma.exception.TeacherIdNotFoundedException
import com.diploma.mappers.AccountMapper
import com.diploma.mappers.TeacherMapper
import com.diploma.repository.TeacherRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class TeacherService {
    @Autowired
    private lateinit var teacherRepository: TeacherRepository

    @Autowired
    private lateinit var teacherMapper: TeacherMapper

    @Autowired
    private lateinit var accountMapper: AccountMapper

    fun getAllTeachers(pageable: Pageable) = this.teacherRepository.findAll(pageable)

    fun getTeacherById(id: Long): Teacher {
        return this.teacherRepository.findById(id).orElseThrow {
            TeacherIdNotFoundedException("Преподаватель не найден")
        }.let {
            teacherMapper.toResponse(it)
        }
    }

    fun save(addTeacher: AddTeacher): Teacher {
        return TeacherEntity(
            description = addTeacher.description,
            account = accountMapper.toEntity(addTeacher.account)
        ).let {
            this.teacherRepository.save(it).let {
                teacherMapper.toResponse(it)
            }
        }
    }

    fun deleteTeacher(id: Long) {
        this.teacherRepository.deleteById(id)
    }

    fun deleteTeachers(ids: Set<Long>) {
        this.teacherRepository.deleteAllById(ids)
    }
}