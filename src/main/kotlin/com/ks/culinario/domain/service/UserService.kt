package com.ks.culinario.domain.service

import com.ks.culinario.domain.model.User

interface UserService {

    fun getAllUsers(): List<User>
    fun getUser(id: Long): User
    fun createUser(user: User): User
    fun deleteUser(id: Long)
    fun updateUser(user: User): User
}
