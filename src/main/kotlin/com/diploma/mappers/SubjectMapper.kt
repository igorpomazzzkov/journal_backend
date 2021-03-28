package com.diploma.mappers

import com.diploma.dto.Subject
import com.diploma.entity.SubjectEntity
import org.springframework.stereotype.Service

@Service
class SubjectMapper {
    fun toResponse(subjectEntity: SubjectEntity): Subject {
        return Subject(
            id = subjectEntity.id,
            name = subjectEntity.name,
            shortName = subjectEntity.shortName
        )
    }
}