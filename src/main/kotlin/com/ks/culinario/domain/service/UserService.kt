package com.ks.culinario.domain.service

import com.ks.culinario.infrastructure.repository.UserRepository
import com.ks.culinario.web.controller.UserDTO
import org.springframework.stereotype.Service

interface UserService{

    fun getAllUsers(): List<UserDTO>
    fun getUser(id: Long): UserDTO
    fun createUser(userDTO: UserDTO)
    fun deleteUser(id: Long)
    fun updateUser(userDTO: UserDTO) {}
}
