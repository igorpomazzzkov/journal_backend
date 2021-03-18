package com.diploma.entity

import javax.persistence.*

@Entity
@Table(name = "groups")
data class GroupEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "name", nullable = false)
    val name: String = "",

    @Column(name = "course", nullable = true)
    val course: Int? = null,

    @OneToMany(mappedBy = "group")
    val students: List<StudentEntity> = emptyList(),

    @OneToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "headman_id", referencedColumnName = "id")
    val headman: StudentEntity? = null
)