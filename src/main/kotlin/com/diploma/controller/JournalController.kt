package com.diploma.controller

import com.diploma.dto.AddJournal
import com.diploma.dto.AddJournalInfo
import com.diploma.dto.Journal
import com.diploma.mappers.JournalInfoMapper
import com.diploma.service.JournalService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("journals")
class JournalController {

    @Autowired
    private lateinit var journalService: JournalService

    @Autowired
    private lateinit var journalInfoMapper: JournalInfoMapper

    @GetMapping
    fun getAllJournals(): List<Journal> {
        return this.journalService.getAllJournals()
    }

    @GetMapping(params = ["journalId"])
    fun getJournalById(@RequestParam journalId: Long) = this.journalService.getJournalById(journalId)

    @GetMapping(params = ["teacherId"])
    fun getJournalsBuTeacherId(@RequestParam teacherId: Long) =
        this.journalService.getJournalsByTeacherId(teacherId)

    @GetMapping(params = ["groupId"])
    fun getJournalsByGroupId(@RequestParam groupId: Long) =
        this.journalService.getJournalsByGroupId(groupId)

    @DeleteMapping(params = ["id"])
    fun deleteJournalById(@RequestParam id: Long) = this.journalService.deleteById(id)

    @DeleteMapping(params = ["ids"])
    fun deleteJournalsByIds(@RequestParam ids: Set<Long>) = this.journalService.deleteByIds(ids)

    @PostMapping
    fun addNewJournal(@RequestBody addJournal: AddJournal) = this.journalService.addNewJournal(addJournal)

    @GetMapping("{id}")
    fun getJournal(@PathVariable id: Long) = this.journalService.getJournalInfo(id).map {
        journalInfoMapper.toResponse(it)
    }.sortedBy { it.date }

    @GetMapping("{id}/header")
    fun getHeader(@PathVariable id: Long) = this.journalService.getHeader(id)

    @GetMapping("{id}/cell")
    fun getCell(@PathVariable id: Long, @RequestParam groupId: Long) = this.journalService.getCell(id, groupId)

    @PostMapping("{id}")
    fun addNewDate(@PathVariable id: Long, @RequestBody addJournalInfo: AddJournalInfo) =
        this.journalService.addNewDateForJournal(id, addJournalInfo)
}
