package com.diploma.service

import com.diploma.entity.GroupEntity
import com.diploma.repository.GroupRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GroupService {
    @Autowired
    private lateinit var groupRepository: GroupRepository

    fun getAllGroups(): List<GroupEntity> {
        return this.groupRepository.findAll()
    }
}