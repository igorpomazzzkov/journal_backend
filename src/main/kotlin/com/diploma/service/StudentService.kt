package com.diploma.service

import com.diploma.dto.AddUser
import com.diploma.dto.UpdateStudent
import com.diploma.entity.Role
import com.diploma.entity.StudentEntity
import com.diploma.entity.UserEntity
import com.diploma.exception.StudentIdNotFoundedException
import com.diploma.exception.TeacherIdNotFoundedException
import com.diploma.mappers.UserMapper
import com.diploma.repository.StudentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class StudentService {
    @Autowired
    private lateinit var studentRepository: StudentRepository

    @Autowired
    private lateinit var groupService: GroupService

    @Autowired
    private lateinit var userMapper: UserMapper

    fun getAllStudents(): List<StudentEntity> {
        return this.studentRepository.findAll()
    }

    fun getStudentById(id: Long): StudentEntity {
        return this.studentRepository.findById(id)
            .orElseThrow { StudentIdNotFoundedException("Student with id $id not founded") }
    }

    fun searchStudent(name: String): List<StudentEntity>? {
        return this.studentRepository.findStudentEntityByCustomQuery(name)
    }

    fun addNewStudent(addUser: AddUser): StudentEntity {
        val studentEntity: StudentEntity = StudentEntity(
            lastName = addUser.lastName,
            firstName = addUser.firstName,
            middleName = addUser.middleName,
            group = addUser.groupId?.let { this.groupService.getGroupById(it) },
            user = UserEntity(
                email = addUser.email,
                mobile = addUser.mobile,
                image = addUser.image,
                password = addUser.password,
                roles = setOf(Role.STUDENT)
            )
        )
        return this.studentRepository.save(studentEntity)
    }

    fun deleteById(id: Long) {
        this.studentRepository.deleteById(id)
    }
}