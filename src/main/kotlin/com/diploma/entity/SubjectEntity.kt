package com.diploma.entity

import javax.persistence.*

@Entity
@Table(name = "subjects")
data class SubjectEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "name")
    val name: String? = null,

    @Column(name = "short_name")
    val shortName: String? = null
)