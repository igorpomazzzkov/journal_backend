package com.diploma.controller

import com.diploma.dto.Teacher
import com.diploma.mappers.TeacherMapper
import com.diploma.service.TeacherService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("teachers")
class TeacherController {

    @Autowired
    private lateinit var teacherService: TeacherService

    @Autowired
    private lateinit var teacherMapper: TeacherMapper

    @GetMapping
    fun getAllTeachers(): List<Teacher> {
        return teacherService.getAllTeachers().map {
            teacherMapper.toResponse(it)
        }
    }

    @GetMapping("{id}")
    fun getTeacherById(@PathVariable id: Long): Teacher {
        return this.teacherService.getTeacherById(id).let {
            teacherMapper.toResponse(it)
        }
    }

    @GetMapping(params = ["name"])
    fun searchTeacher(@RequestParam name: String): List<Teacher>? {
        return this.teacherService.searchTeacher(name)?.map {
            teacherMapper.toResponse(it)
        }
    }

    @DeleteMapping("{id}")
    fun deleteGroup(@PathVariable id: Long) {
        this.teacherService.deleteById(id)
    }
}