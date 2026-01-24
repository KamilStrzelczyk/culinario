package com.ks.culinario.infrastructure.service

import LoginRequestDTO
import com.ks.culinario.data.dao.TokenDao
import com.ks.culinario.data.dao.UserDao
import com.ks.culinario.data.entity.TokenEntity
import com.ks.culinario.data.entity.TokenType
import com.ks.culinario.data.entity.UserEntity
import com.ks.culinario.data.mapper.UserMapper
import com.ks.culinario.domain.exception.InvalidCredentialsException
import com.ks.culinario.domain.exception.ResourceNotFoundException
import com.ks.culinario.domain.service.AuthService
import com.ks.culinario.network.dto.AuthResponseDTO
import com.ks.culinario.network.dto.RefreshTokenRequest
import com.ks.culinario.network.security.JwtTokenProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.UUID

@Service
class AuthServiceImpl(
    private val userDao: UserDao,
    private val tokenDao: TokenDao,
    private val jwtTokenProvider: JwtTokenProvider,
    private val passwordEncoder: PasswordEncoder,
    private val userMapper: UserMapper
) : AuthService {

    @Value("\${app.jwt.expiration-in-ms}")
    private lateinit var accessExpirationMs: String

    @Value("\${app.jwt.refresh-expiration-in-ms}")
    private lateinit var refreshExpirationMs: String

    @Transactional
    override fun login(request: LoginRequestDTO): AuthResponseDTO {
        val userEntity = userDao.findByUsername(request.username).orElseThrow {
            ResourceNotFoundException("User not found with username: ${request.username}")
        }

        if (!passwordEncoder.matches(request.password, userEntity.password)) {
            throw InvalidCredentialsException("Bad credentials")
        }

        revokeAllUserTokens(userEntity)

        val accessToken = jwtTokenProvider.generateToken(userMapper.toDomain(userEntity))
        val refreshToken = UUID.randomUUID().toString()

        saveToken(userEntity, accessToken, TokenType.ACCESS, accessExpirationMs.toLong())
        saveToken(userEntity, refreshToken, TokenType.REFRESH, refreshExpirationMs.toLong())

        return AuthResponseDTO(accessToken, refreshToken)
    }

    @Transactional
    override fun logout(username: String) {
        val userEntity = userDao.findByUsername(username).orElseThrow {
            ResourceNotFoundException("User not found with username: $username")
        }
        revokeAllUserTokens(userEntity)
    }

    @Transactional
    override fun refresh(request: RefreshTokenRequest): AuthResponseDTO {
        val requestToken = request.refreshToken
        
        val tokenEntity = tokenDao.findByToken(requestToken).orElseThrow {
            RuntimeException("Refresh token is not in database!")
        }

        if (tokenEntity.revoked) {
            throw RuntimeException("Refresh token was revoked")
        }
            
        verifyExpiration(tokenEntity)
        
        val userEntity = tokenEntity.user!!
        val userDomain = userMapper.toDomain(userEntity)
        
        val accessToken = jwtTokenProvider.generateToken(userDomain)
        
        saveToken(userEntity, accessToken, TokenType.ACCESS, accessExpirationMs.toLong())
        
        return AuthResponseDTO(accessToken, requestToken)
    }

    private fun saveToken(user: UserEntity, token: String, type: TokenType, expirationMs: Long) {
        val tokenEntity = TokenEntity(
            user = user,
            token = token,
            tokenType = type,
            expiryDate = Instant.now().plusMillis(expirationMs),
            revoked = false
        )
        tokenDao.save(tokenEntity)
    }

    private fun revokeAllUserTokens(user: UserEntity) {
        val validUserTokens = tokenDao.findAllByUser(user)
        if (validUserTokens.isEmpty()) return
        
        validUserTokens.forEach { token ->
            tokenDao.delete(token)
        }
    }

    private fun verifyExpiration(token: TokenEntity) {
        if (token.expiryDate.compareTo(Instant.now()) < 0) {
            tokenDao.delete(token)
            throw RuntimeException("Token was expired")
        }
    }
}
