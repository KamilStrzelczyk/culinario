package com.ks.culinario.infrastructure.service

import LoginRequestDTO
import com.ks.culinario.domain.exception.InvalidCredentialsException
import com.ks.culinario.domain.exception.ResourceNotFoundException
import com.ks.culinario.domain.model.Token
import com.ks.culinario.domain.model.TokenType
import com.ks.culinario.domain.model.User
import com.ks.culinario.domain.repository.TokenRepository
import com.ks.culinario.domain.repository.UserRepository
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
    private val userRepository: UserRepository,
    private val tokenRepository: TokenRepository,
    private val jwtTokenProvider: JwtTokenProvider,
    private val passwordEncoder: PasswordEncoder
) : AuthService {

    @Value("\${app.jwt.expiration-in-ms}")
    private lateinit var accessExpirationMs: String

    @Value("\${app.jwt.refresh-expiration-in-ms}")
    private lateinit var refreshExpirationMs: String

    @Transactional
    override fun login(request: LoginRequestDTO): AuthResponseDTO {
        val user = userRepository.findByUsername(request.username)
            ?: throw ResourceNotFoundException("User not found with username: ${request.username}")

        if (!passwordEncoder.matches(request.password, user.password)) {
            throw InvalidCredentialsException("Bad credentials")
        }

        revokeAllUserTokens(user)

        val accessToken = jwtTokenProvider.generateToken(user)
        val refreshToken = UUID.randomUUID().toString()

        saveToken(user, accessToken, TokenType.ACCESS, accessExpirationMs.toLong())
        saveToken(user, refreshToken, TokenType.REFRESH, refreshExpirationMs.toLong())

        return AuthResponseDTO(accessToken, refreshToken)
    }

    @Transactional
    override fun logout(username: String) {
        val user = userRepository.findByUsername(username)
            ?: throw ResourceNotFoundException("User not found with username: $username")
        revokeAllUserTokens(user)
    }

    @Transactional
    override fun refresh(request: RefreshTokenRequest): AuthResponseDTO {
        val requestToken = request.refreshToken
        
        val token = tokenRepository.findByToken(requestToken)
            ?: throw RuntimeException("Refresh token is not in database!")

        if (token.revoked) {
            throw RuntimeException("Refresh token was revoked")
        }
            
        verifyExpiration(token)
        
        val user = token.user
        val accessToken = jwtTokenProvider.generateToken(user)
        
        saveToken(user, accessToken, TokenType.ACCESS, accessExpirationMs.toLong())
        
        return AuthResponseDTO(accessToken, requestToken)
    }

    private fun saveToken(user: User, tokenString: String, type: TokenType, expirationMs: Long) {
        val token = Token(
            user = user,
            token = tokenString,
            tokenType = type,
            expiryDate = Instant.now().plusMillis(expirationMs),
            revoked = false
        )
        tokenRepository.save(token)
    }

    private fun revokeAllUserTokens(user: User) {
        val validUserTokens = tokenRepository.findAllByUser(user)
        if (validUserTokens.isEmpty()) return
        
        validUserTokens.forEach { token ->
            tokenRepository.delete(token)
        }
    }

    private fun verifyExpiration(token: Token) {
        if (token.expiryDate.compareTo(Instant.now()) < 0) {
            tokenRepository.delete(token)
            throw RuntimeException("Token was expired")
        }
    }
}
