package com.diploma.service

import com.diploma.dto.AddGroup
import com.diploma.dto.UpdateGroup
import com.diploma.entity.GroupEntity
import com.diploma.exception.GroupIdNotFoundedException
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

    fun getGroupById(id: Long): GroupEntity {
        return this.groupRepository.existsById(id).takeIf { it }?.let {
            this.groupRepository.findById(id).get()
        } ?: run {
            throw GroupIdNotFoundedException("Group not founded with id $id")
        }
    }

    fun getGroupsByName(name: String): List<GroupEntity>? {
        return this.groupRepository.findAllByNameIsContaining(name)
    }

    fun addNewGroup(addGroup: AddGroup): GroupEntity {
        val groupToDB: GroupEntity = GroupEntity(
            name = addGroup.name,
            course = addGroup.course
        )
        return this.groupRepository.save(groupToDB)
    }

    fun updateGroup(id: Long, updateGroup: UpdateGroup): GroupEntity {
        val groupFromDB = this.groupRepository.findById(id)
            .orElseThrow { throw GroupIdNotFoundedException("Group with $id not founded") }
        return this.groupRepository.save(groupFromDB.copy(name = updateGroup.name, course = updateGroup.course))
    }

    fun deleteById(id: Long) {
        this.groupRepository.deleteById(id)
    }

    fun deleteByIds(ids: Set<Long>) {
        this.groupRepository.deleteAllById(ids)
    }
}