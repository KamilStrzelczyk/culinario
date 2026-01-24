package com.ks.culinario.domain.model

import java.time.Instant

data class Token(
    val id: Long? = null,
    val token: String,
    val tokenType: TokenType,
    val user: User,
    val expiryDate: Instant,
    val revoked: Boolean
)
