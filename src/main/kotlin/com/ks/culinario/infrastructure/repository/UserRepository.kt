package com.ks.culinario.infrastructure.repository

import com.ks.culinario.web.controller.UserDTO

interface UserRepository {

    fun findAll(): List<UserDTO>

    fun findById(id: Long): UserDTO

    fun save(userDTO: UserDTO): Boolean

    fun deleteById(id: Long)
}