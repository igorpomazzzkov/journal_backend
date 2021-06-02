package com.diploma.controller

import com.diploma.dto.AddStudent
import com.diploma.service.StudentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("students")
class StudentController {
    @Autowired
    private lateinit var studentService: StudentService

    @GetMapping
    fun getAllStudents() = this.studentService.getAll()

    @GetMapping("pageable")
    fun getAllStudents(pageable: Pageable) = this.studentService.getAllStudents(pageable)

    @GetMapping("{id}")
    fun getStudentById(@PathVariable id: Long) = this.studentService.getStudentById(id)

    @GetMapping(params = ["groupId"])
    fun getAllStudentsByGroupId(@RequestParam groupId: Long) = this.studentService.getAllStudentsByGroupId(groupId)

    @GetMapping(params = ["journalId"])
    fun getAllStudentsByJournalId(@RequestParam journalId: Long) = this.studentService.getAllStudentsByJournalId(journalId)

    @PostMapping
    fun save(@RequestBody addStudent: AddStudent) = this.studentService.save(addStudent)

    @DeleteMapping(params = ["id"])
    fun deleteStudent(@RequestParam id: Long) {
        this.studentService.deleteById(id)
    }

    @DeleteMapping(params = ["ids"])
    fun deleteStudents(@RequestParam ids: Set<Long>) {
        this.studentService.deleteByIds(ids)
    }
}