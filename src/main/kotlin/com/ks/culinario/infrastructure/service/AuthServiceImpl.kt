package com.ks.culinario.infrastructure.service

import LoginRequestDTO
import com.ks.culinario.domain.exception.InvalidCredentialsException
import com.ks.culinario.domain.exception.ResourceNotFoundException
import com.ks.culinario.domain.repository.UserRepository
import com.ks.culinario.domain.service.AuthService
import com.ks.culinario.network.security.JwtTokenProvider
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(
    private val userRepository: UserRepository,
    private val jwtTokenProvider: JwtTokenProvider,
    private val passwordEncoder: PasswordEncoder
) : AuthService {
    override fun login(request: LoginRequestDTO): String {
        val user = userRepository.findByUsername(request.username)
            ?: throw ResourceNotFoundException("User not found with username: ${request.username}")

        if (!passwordEncoder.matches(request.password, user.password)) {
            throw InvalidCredentialsException("Bad credentials")
        }

        val token = jwtTokenProvider.generateToken(user)
        return token
    }

    override fun logout(): Boolean {
        TODO("Not yet implemented")
    }

    override fun refresh(): Boolean {
        TODO("Not yet implemented")
    }
}
