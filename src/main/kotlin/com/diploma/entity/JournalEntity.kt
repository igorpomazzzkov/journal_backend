package com.diploma.entity

import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "journals")
class JournalEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id", referencedColumnName = "id", insertable = false, updatable = false)
    val group: GroupEntity? = null,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id", referencedColumnName = "id", insertable = false, updatable = false)
    val subject: SubjectEntity? = null,

    @Column(name = "teacher_id")
    val teacherId: Long? = null,

    @Column(name = "subject_id")
    val subjectId: Long? = null,

    @Column(name = "group_id")
    val groupId: Long? = null,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id", referencedColumnName = "id", insertable = false, updatable = false)
    val account: AccountEntity? = null,

    @Column(name = "created_date")
    val createdDate: Timestamp? = null,

    @Column(name = "last_updated")
    val lastUpdated: Timestamp? = null
)