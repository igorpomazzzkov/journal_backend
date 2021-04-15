package com.diploma.controller

import com.diploma.dto.AddSubject
import com.diploma.dto.Subject
import com.diploma.dto.UpdateSubject
import com.diploma.mappers.SubjectMapper
import com.diploma.service.SubjectService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("subjects")
class SubjectController {
    @Autowired
    private lateinit var subjectService: SubjectService

    @Autowired
    private lateinit var subjectMapper: SubjectMapper

    @GetMapping
    fun getAllSubjects(): List<Subject> {
        return this.subjectService.getAllSubjects().map {
            subjectMapper.toResponse(it)
        }
    }

    @GetMapping(params = ["name"])
    fun searchSubject(@RequestParam name: String): List<Subject>? {
        return this.subjectService.searchSubject(name)?.map {
            subjectMapper.toResponse(it)
        }
    }

    @PostMapping
    fun addNewSubject(@RequestBody addSubject: AddSubject): Subject {
        return this.subjectService.addSubject(addSubject).let {
            subjectMapper.toResponse(it)
        }
    }

    @PutMapping("{id}")
    fun updateSubject(@PathVariable id: Long, @RequestBody updateSubject: UpdateSubject): Subject {
        return this.subjectService.updateSubject(id, updateSubject).let {
            subjectMapper.toResponse(it)
        }
    }

    @DeleteMapping("{id}")
    fun deleteGroup(@PathVariable id: Long) {
        this.subjectService.deleteById(id)
    }

    @DeleteMapping(params = ["ids"])
    fun deleteGroups(@RequestParam ids: Set<Long>) {
        this.subjectService.deleteByIds(ids)
    }
}