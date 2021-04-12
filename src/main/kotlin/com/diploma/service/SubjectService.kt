package com.diploma.service

import com.diploma.dto.AddSubject
import com.diploma.dto.UpdateSubject
import com.diploma.entity.SubjectEntity
import com.diploma.exception.SubjectIdNotFoundedException
import com.diploma.repository.SubjectRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SubjectService {
    @Autowired
    private lateinit var subjectRepository: SubjectRepository

    fun getAllSubjects(): List<SubjectEntity> {
        return this.subjectRepository.findAll()
    }

    fun searchSubject(name: String): List<SubjectEntity>? {
        return this.subjectRepository.findAllByNameIsContaining(name)
    }

    fun addSubject(addSubject: AddSubject): SubjectEntity {
        val subjectEntity: SubjectEntity = SubjectEntity(
            name = addSubject.name,
            shortName = addSubject.shortName
        )
        return this.subjectRepository.save(subjectEntity)
    }

    fun updateSubject(id: Long, updateSubject: UpdateSubject): SubjectEntity {
        val subjectFromDB = this.subjectRepository.findById(id)
            .orElseThrow { throw SubjectIdNotFoundedException("Subject with id $id not founded") }
        return this.subjectRepository.save(
            subjectFromDB.copy(
                name = updateSubject.name,
                shortName = updateSubject.shortName
            )
        )
    }

    fun deleteById(id: Long) {
        this.subjectRepository.deleteById(id)
    }

    fun deleteByIds(ids: Set<Long>) {
        this.subjectRepository.deleteAllById(ids)
    }
}