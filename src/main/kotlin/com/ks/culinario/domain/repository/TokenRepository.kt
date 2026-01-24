package com.ks.culinario.domain.repository

import com.ks.culinario.domain.model.Token
import com.ks.culinario.domain.model.User
import java.util.Optional

interface TokenRepository {
    fun findByToken(token: String): Token?
    fun findAllByUser(user: User): List<Token>
    fun save(token: Token): Token
    fun delete(token: Token)
    fun deleteByUser(user: User)
}
