package com.ks.culinario.data.mapper

import com.ks.culinario.data.entity.UserEntity
import com.ks.culinario.domain.model.User
import com.ks.culinario.network.dto.NewUserDTO
import com.ks.culinario.network.dto.UserDTO
import org.springframework.stereotype.Component

@Component
class UserMapper {

    fun toDomain(entity: UserEntity): User {
        return User(
            id = entity.id,
            username = entity.username,
            email = entity.email,
            password = entity.password
        )
    }

    fun toDomain(dto: NewUserDTO): User {
        return User(
            id = null,
            username = dto.name,
            email = dto.email,
            password = dto.password
        )
    }

    fun toEntity(domain: User): UserEntity {
        return UserEntity(
            id = domain.id,
            username = domain.username,
            email = domain.email,
            password = domain.password,
        )
    }

    fun toDTO(domain: User): UserDTO {
        return UserDTO(
            id = domain.id!!,
            name = domain.username,
            email = domain.email
        )
    }
}
