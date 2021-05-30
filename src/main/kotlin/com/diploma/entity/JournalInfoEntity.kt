package com.diploma.entity

import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "journal_info")
class JournalInfoEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "mark")
    val mark: Int? = null,

    @Column(name = "journal_id", insertable = false, updatable = false)
    val journalId: Long? = null,

    @ManyToOne
    @JoinColumn(name = "student_id")
    val student: StudentEntity? = null,

    @Column(name = "student_id", insertable = false, updatable = false)
    val studentId: Long? = null,

    @Column(name = "mark_type")
    val markType: String? = null,

    val date: Timestamp? = null,

    @ManyToOne(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.ALL],
        targetEntity = JournalEntity::class
    )
    @JoinColumn(name = "journal_id", referencedColumnName = "id")
    val journals: JournalEntity? = null,

    @Column(name = "desc", nullable = true)
    val description: String? = null
)

enum class MartType(val id: Int) {
    LECTURE(1), LABORATORY(2), SEMINAR(3), TEST(4)
}