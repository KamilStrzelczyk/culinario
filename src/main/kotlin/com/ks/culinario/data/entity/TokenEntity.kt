package com.ks.culinario.data.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "tokens")
data class TokenEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, unique = true)
    val token: String = "",

    @Enumerated(EnumType.STRING)
    val tokenType: TokenType = TokenType.ACCESS,

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    val user: UserEntity? = null,

    @Column(nullable = false)
    val expiryDate: Instant = Instant.now(),
    
    val revoked: Boolean = false
)
