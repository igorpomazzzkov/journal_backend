package com.diploma.entity

import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "accounts")
class AccountEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "first_name", nullable = true)
    val firstName: String? = null,

    @Column(name = "last_name", nullable = true)
    val lastName: String? = null,

    @Column(name = "middle_name", nullable = true)
    val middleName: String? = null,

    @Column(name = "address", nullable = true)
    val address: String? = null,

    @Column(name = "image", nullable = true)
    val image: String? = null,

    @Column(name = "birthday", nullable = true)
    val birthday: Timestamp? = null,

    @OneToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    val user: UserEntity? = null,
)