package com.diploma.controller

import com.diploma.dto.AddGroup
import com.diploma.dto.AuthGroup
import com.diploma.dto.Group
import com.diploma.dto.UpdateGroup
import com.diploma.entity.GroupEntity
import com.diploma.mappers.GroupMapper
import com.diploma.service.GroupService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

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
    
    @GetMapping("names")
    fun getAllgroupsOnlyName(): List<AuthGroup> {
        return this.groupService.getAllGroups().map {
            AuthGroup(id = it.id, name = it.name)
        }
    }

    @GetMapping("{id}")
    fun getGroupById(@PathVariable id: Long): Group {
        return this.groupService.getGroupById(id).let {
            groupMapper.toResponse(it)
        }
    }

    @GetMapping(params = ["name"])
    fun getGroupByName(@RequestParam name: String): List<Group>? {
        return this.groupService.getGroupsByName(name)?.map {
            groupMapper.toResponse(it)
        }
    }

    @PostMapping
    fun addNewGroup(@RequestBody addGroup: AddGroup): Group {
        return this.groupService.addNewGroup(addGroup).let {
            this.groupMapper.toResponse(it)
        }
    }

    @PutMapping("{id}")
    fun updateGroup(@PathVariable id: Long, @RequestBody updateGroup: UpdateGroup): Group {
        return this.groupService.updateGroup(id, updateGroup).let {
            groupMapper.toResponse(it)
        }
    }

    @DeleteMapping("{id}")
    fun deleteGroup(@PathVariable id: Long) {
        this.groupService.deleteById(id)
    }

    @DeleteMapping(params = ["ids"])
    fun deleteGroups(@RequestParam ids: Set<Long>) {
        this.groupService.deleteByIds(ids)
    }
}