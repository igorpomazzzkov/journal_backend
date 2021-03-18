package com.diploma.repository

import com.diploma.entity.RoleEntity
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository: JpaRepository<RoleEntity, Long> {
    fun findByName(name: String): RoleEntity
}