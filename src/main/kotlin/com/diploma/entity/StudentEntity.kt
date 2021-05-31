package com.diploma.entity

import javax.persistence.*

@Entity
@Table(name = "students")
data class StudentEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "identifier")
    var identifier: String? = null,

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    val account: AccountEntity? = null,

    @Column(name = "account_id", insertable = false, updatable = false)
    var accountId: Long? = null,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    val group: GroupEntity? = null,

    @Column(name = "group_id", insertable = false, updatable = false)
    var groupId: Long? = null,

    @Column(name = "telegram_chat_id", nullable = true)
    var telegramChatId: Long? = null
)
