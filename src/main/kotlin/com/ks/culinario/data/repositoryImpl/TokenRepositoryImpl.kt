package com.ks.culinario.data.repositoryImpl

import com.ks.culinario.data.dao.TokenDao
import com.ks.culinario.data.mapper.TokenMapper
import com.ks.culinario.data.mapper.UserMapper
import com.ks.culinario.domain.model.Token
import com.ks.culinario.domain.model.User
import com.ks.culinario.domain.repository.TokenRepository
import org.springframework.stereotype.Repository

@Repository
class TokenRepositoryImpl(
    private val tokenDao: TokenDao,
    private val tokenMapper: TokenMapper,
    private val userMapper: UserMapper
) : TokenRepository {

    override fun findByToken(token: String): Token? {
        return tokenDao.findByToken(token).map { tokenMapper.toDomain(it) }.orElse(null)
    }

    override fun findAllByUser(user: User): List<Token> {
        val userEntity = userMapper.toEntity(user)
        return tokenDao.findAllByUser(userEntity).map { tokenMapper.toDomain(it) }
    }

    override fun save(token: Token): Token {
        val entity = tokenMapper.toEntity(token)
        val savedEntity = tokenDao.save(entity)
        return tokenMapper.toDomain(savedEntity)
    }

    override fun delete(token: Token) {
        val entity = tokenMapper.toEntity(token)
        tokenDao.delete(entity)
    }

    override fun deleteByUser(user: User) {
        val userEntity = userMapper.toEntity(user)
        tokenDao.deleteByUser(userEntity)
    }
}
