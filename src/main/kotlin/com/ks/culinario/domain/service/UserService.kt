package com.ks.culinario.domain.service

import com.ks.culinario.network.dto.NewUserDTO
import com.ks.culinario.network.dto.UserDTO

interface UserService {

    fun getAllUsers(): List<UserDTO>
    fun getUser(id: Long): UserDTO
    fun createUser(newUserDTO: NewUserDTO)
    fun deleteUser(id: Long)
    fun updateUser(userDTO: UserDTO)
}
