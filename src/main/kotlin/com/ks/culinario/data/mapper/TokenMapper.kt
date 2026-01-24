package com.ks.culinario.data.mapper

import com.ks.culinario.data.entity.TokenEntity
import com.ks.culinario.domain.model.Token
import org.springframework.stereotype.Component

@Component
class TokenMapper(
    private val userMapper: UserMapper
) {

    fun toDomain(entity: TokenEntity): Token {
        return Token(
            id = entity.id,
            token = entity.token,
            tokenType = entity.tokenType,
            user = userMapper.toDomain(entity.user!!),
            expiryDate = entity.expiryDate,
            revoked = entity.revoked
        )
    }

    fun toEntity(domain: Token): TokenEntity {
        return TokenEntity(
            id = domain.id,
            token = domain.token,
            tokenType = domain.tokenType,
            user = userMapper.toEntity(domain.user),
            expiryDate = domain.expiryDate,
            revoked = domain.revoked
        )
    }
}
