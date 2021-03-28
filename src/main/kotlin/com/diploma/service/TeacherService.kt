package com.diploma.service

import com.diploma.dto.AddTeacher
import com.diploma.dto.AddUser
import com.diploma.dto.UpdateTeacher
import com.diploma.entity.Role
import com.diploma.entity.TeacherEntity
import com.diploma.entity.UserEntity
import com.diploma.exception.TeacherIdNotFoundedException
import com.diploma.mappers.UserMapper
import com.diploma.repository.TeacherRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TeacherService {

    @Autowired
    private lateinit var teacherRepository: TeacherRepository

    @Autowired
    private lateinit var userMapper: UserMapper

    fun getAllTeachers(): List<TeacherEntity> {
        return this.teacherRepository.findAll()
    }

    fun getTeacherById(id: Long): TeacherEntity {
        return this.teacherRepository.findById(id)
            .orElseThrow { TeacherIdNotFoundedException("Teacher with id $id not founded") }
    }

    fun searchTeacher(name: String): List<TeacherEntity>? {
        return this.teacherRepository.findTeachersByCustomQuery(name)
    }

    fun addNewTeacher(addUser: AddUser): TeacherEntity {
        val teacherEntity: TeacherEntity = TeacherEntity(
            lastName = addUser.lastName,
            firstName = addUser.firstName,
            middleName = addUser.middleName,
            user = UserEntity(
                email = addUser.email,
                mobile = addUser.mobile,
                image = addUser.image,
                password = addUser.password,
                roles = setOf(Role.STUDENT)
            )
        )
        return this.teacherRepository.save(teacherEntity)
    }

    fun deleteById(id: Long) {
        this.teacherRepository.deleteById(id)
    }
}