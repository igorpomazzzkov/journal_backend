package com.diploma.entity

import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "journals")
class JournalEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    val group: GroupEntity? = null,

    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    val subject: SubjectEntity? = null,

    @Column(name = "teacher_id", insertable = false, updatable = false)
    val teacherId: Long? = null,

    @Column(name = "subject_id", insertable = false, updatable = false)
    val subjectId: Long? = null,

    @Column(name = "group_id", insertable = false, updatable = false)
    val groupId: Long? = null,

    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    val teacherEntity: TeacherEntity? = null,

    @Column(name = "created_date")
    val createdDate: Timestamp? = null,

    @Column(name = "last_updated")
    val lastUpdated: Timestamp? = null
)