package com.diploma.controller

import com.diploma.dto.Student
import com.diploma.dto.UpdateStudent
import com.diploma.mappers.StudentMapper
import com.diploma.service.StudentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("students")
class StudentController {
    @Autowired
    private lateinit var studentService: StudentService

    @Autowired
    private lateinit var studentMapper: StudentMapper

    @GetMapping
    fun getAllSubjects(): List<Student> {
        return this.studentService.getAllStudents().map {
            studentMapper.toResponse(it)
        }
    }

    @GetMapping(params = ["name"])
    fun searchSubject(@RequestParam name: String): List<Student>? {
        return this.studentService.searchStudent(name)?.map {
            studentMapper.toResponse(it)
        }
    }

    @DeleteMapping("{id}")
    fun deleteGroup(@PathVariable id: Long) {
        this.studentService.deleteById(id)
    }
}