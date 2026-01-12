package com.ks.culinario.data.mapper

import com.ks.culinario.domain.model.User
import com.ks.culinario.data.entity.UserEntity
import org.springframework.stereotype.Component

@Component
class UserMapper {

    fun toDomain(entity: UserEntity): User {
        return User(
            id = entity.id,
            username = entity.username,
            email = entity.email
        )
    }

    fun toEntity(domain: User): UserEntity {
        return UserEntity(
            id = domain.id,
            username = domain.username,
            email = domain.email
        )
    }
}
