package com.ks.culinario.infrastructure.repositoryImpl

import com.ks.culinario.infrastructure.repository.UserRepository
import com.ks.culinario.web.controller.UserDTO
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(): UserRepository {
    override fun findAll(): List<UserDTO> {
        TODO("Not yet implemented")
    }

    override fun findById(id: Long): UserDTO {
        TODO("Not yet implemented")
    }

    override fun save(userDTO: UserDTO): Boolean {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: Long) {
        TODO("Not yet implemented")
    }
}