package com.diploma.controller

import com.diploma.dto.AddTeacher
import com.diploma.service.TeacherService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("teachers")
class TeacherController {
    @Autowired
    private lateinit var teacherService: TeacherService

    @GetMapping
    fun getAllTeachers() = this.teacherService.getAllTeachers()

    @GetMapping("{id}")
    fun getTeacherById(@PathVariable id: Long) = this.teacherService.getTeacherById(id)

    @DeleteMapping(params = ["id"])
    fun deleteTeacherById(@RequestParam id: Long) = this.teacherService.deleteTeacher(id)

    @DeleteMapping(params = ["ids"])
    fun deleteTeachersByIds(@RequestParam ids: Set<Long>) = this.teacherService.deleteTeachers(ids)

    @PostMapping
    fun addNewTeacher(@RequestBody addTeacher: AddTeacher) = this.teacherService.save(addTeacher)
}