package com.diploma.entity

import javax.persistence.*

@Entity
@Table(name = "users")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "email", unique = true, nullable = false)
    val email: String,

    @Column(name = "password", nullable = false)
    var password: String? = null,

    @Column(name = "mobile", nullable = true)
    var mobile: String? = null,

    @Column(name = "image", nullable = true)
    var image: String? = null,

    @ElementCollection(targetClass = Role::class, fetch = FetchType.EAGER)
    @CollectionTable(
        name = "roles",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    val roles: Set<Role> = emptySet()
)

enum class Role {
    TEACHER, ADMIN, STUDENT
}