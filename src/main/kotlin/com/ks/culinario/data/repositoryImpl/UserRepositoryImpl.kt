package com.ks.culinario.data.repositoryImpl

import com.ks.culinario.domain.model.User
import com.ks.culinario.domain.repository.UserRepository
import com.ks.culinario.data.entity.UserEntity
import com.ks.culinario.data.mapper.UserMapper
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    private val userMapper: UserMapper
) : UserRepository {

    private val database = mutableMapOf<Long, UserEntity>()
    private var idCounter = 1L

    override fun findAll(): List<User> {
        return database.values.map { userMapper.toDomain(it) }
    }

    override fun findById(id: Long): User? {
        val entity = database[id]
        return entity?.let { userMapper.toDomain(it) }
    }

    override fun save(user: User): User {
        val entity = userMapper.toEntity(user)
        val id = entity.id ?: idCounter++
        val savedEntity = entity.copy(id = id)
        database[id] = savedEntity
        return userMapper.toDomain(savedEntity)
    }

    override fun deleteById(id: Long) {
        database.remove(id)
    }
}
