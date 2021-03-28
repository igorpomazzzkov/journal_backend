package com.diploma.entity

import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "journals")
class JournalEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @OneToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    val group: GroupEntity? = null,

    @OneToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    val subject: SubjectEntity? = null,

    @Column(name = "created_date")
    val createdDate: Timestamp? = null,

    @Column(name = "last_updated")
    val lastUpdated: Timestamp? = null,

    @OneToMany(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.ALL],
        targetEntity = JournalInfoEntity::class
    )
    @JoinColumn(name = "journal_id", referencedColumnName = "id")
    val journals: List<JournalInfoEntity>? = null
)