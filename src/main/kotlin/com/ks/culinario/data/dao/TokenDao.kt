package com.ks.culinario.data.dao

import com.ks.culinario.data.entity.TokenEntity
import com.ks.culinario.data.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface TokenDao : JpaRepository<TokenEntity, Long> {
    fun findByToken(token: String): Optional<TokenEntity>
    fun findAllByUser(user: UserEntity): List<TokenEntity>
    fun deleteByUser(user: UserEntity)
}
