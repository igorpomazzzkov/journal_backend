package com.diploma.controller

import com.diploma.dto.Group
import com.diploma.entity.GroupEntity
import com.diploma.mappers.GroupMapper
import com.diploma.service.GroupService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("groups")
class GroupController {

    @Autowired
    private lateinit var groupService: GroupService

    @Autowired
    private lateinit var groupMapper: GroupMapper

    @GetMapping
    fun getAllGroups(): List<Group> {
        return this.groupService.getAllGroups().map {
            groupMapper.toResponse(it)
        }
    }
}