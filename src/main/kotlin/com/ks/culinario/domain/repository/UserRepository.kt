package com.ks.culinario.domain.repository

import com.ks.culinario.domain.model.User

interface UserRepository {

    fun findAll(): List<User>

    fun findById(id: Long): User?

    fun save(user: User): User

    fun deleteById(id: Long)
}
