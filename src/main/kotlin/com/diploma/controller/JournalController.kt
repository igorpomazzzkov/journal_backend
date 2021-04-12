package com.diploma.controller

import com.diploma.dto.AddJournal
import com.diploma.dto.Journal
import com.diploma.service.JournalService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("journals")
class JournalController {

    @Autowired
    private lateinit var journalService: JournalService

    @GetMapping
    fun getAllJournals(): List<Journal> {
        return this.journalService.getAllJournals()
    }

    @GetMapping(params = ["teacherId"])
    fun getJournalsBuTeacherId(@RequestParam teacherId: Long) =
        this.journalService.getJournalsByTeacherId(teacherId)

    @GetMapping(params = ["groupId"])
    fun getJournalsByGroupId(@RequestParam groupId: Long) =
        this.journalService.getJournalsByGroupId(groupId)

    @DeleteMapping(params = ["id"])
    fun deleteJournalById(@RequestParam id: Long) = this.journalService.deleteById(id)

    @DeleteMapping
    fun deleteJournalsByIds(@RequestParam ids: Set<Long>) = this.journalService.deleteByIds(ids)

    @PostMapping
    fun addNewJournal(@RequestBody addJournal: AddJournal) = this.journalService.addNewJournal(addJournal)
}