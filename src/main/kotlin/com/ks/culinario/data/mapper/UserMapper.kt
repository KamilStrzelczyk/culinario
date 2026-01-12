package com.ks.culinario.data.mapper

import com.ks.culinario.domain.model.User
import com.ks.culinario.data.entity.UserEntity
import com.ks.culinario.network.dto.UserDTO
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


    fun toDTO(domain: User): UserDTO {
        return UserDTO(
            id = domain.id,
            username = domain.username,
            email = domain.email
        )
    }

    fun toDomain(dto: UserDTO): User {
        return User(
            id = dto.id,
            username = dto.username,
            email = dto.email
        )
    }
}
