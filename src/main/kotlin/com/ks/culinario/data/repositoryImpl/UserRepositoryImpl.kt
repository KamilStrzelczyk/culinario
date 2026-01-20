package com.ks.culinario.data.repositoryImpl

import com.ks.culinario.data.dao.UserDao
import com.ks.culinario.data.mapper.UserMapper
import com.ks.culinario.domain.model.User
import com.ks.culinario.domain.repository.UserRepository
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    private val userDao: UserDao,
    private val userMapper: UserMapper
) : UserRepository {

    override fun findAll(): List<User> {
        return userDao.findAll().map { userMapper.toDomain(it) }
    }

    override fun findById(id: Long): User? {
        val entity = userDao.findById(id).orElse(null)
        return entity?.let { userMapper.toDomain(it) }
    }

    override fun save(user: User): User {
        val entity = userMapper.toEntity(user)
        val savedEntity = userDao.save(entity)
        return userMapper.toDomain(savedEntity)
    }

    override fun deleteById(id: Long) {
        userDao.deleteById(id)
    }

    override fun findByUsername(username: String): User? {
        val entity = userDao.findByUsername(username).orElse(null)
        return entity?.let { userMapper.toDomain(it) }
    }
}
