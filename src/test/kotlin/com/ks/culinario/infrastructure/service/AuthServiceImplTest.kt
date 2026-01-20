package com.ks.culinario.infrastructure.service

import LoginRequestDTO
import com.ks.culinario.domain.exception.InvalidCredentialsException
import com.ks.culinario.domain.exception.ResourceNotFoundException
import com.ks.culinario.domain.model.User
import com.ks.culinario.domain.repository.UserRepository
import com.ks.culinario.network.security.JwtTokenProvider
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.security.crypto.password.PasswordEncoder

class AuthServiceImplTest {

    private val userRepository: UserRepository = mockk()
    private val jwtTokenProvider: JwtTokenProvider = mockk()
    private val passwordEncoder: PasswordEncoder = mockk()
    private val authService = AuthServiceImpl(userRepository, jwtTokenProvider, passwordEncoder)

    @Test
    fun `should return token when login is successful`() {
        // GIVEN
        val request = LoginRequestDTO("admin", "admin123")
        val user = User(1L, "admin", "admin@example.com", "hashedPassword")
        val expectedToken = "jwt-token"

        every { userRepository.findByUsername(request.username) } returns user
        every { passwordEncoder.matches(request.password, user.password) } returns true
        every { jwtTokenProvider.generateToken(user) } returns expectedToken

        // WHEN
        val result = authService.login(request)

        // THEN
        assertEquals(expectedToken, result)
        verify(exactly = 1) { userRepository.findByUsername(request.username) }
        verify(exactly = 1) { passwordEncoder.matches(request.password, user.password) }
        verify(exactly = 1) { jwtTokenProvider.generateToken(user) }
    }

    @Test
    fun `should throw exception when user is not found`() {
        // GIVEN
        val request = LoginRequestDTO("unknown", "password")
        val user = User(1L, "admin", "admin@example.com", "hashedPassword")
        every { userRepository.findByUsername(request.username) } returns null

        // WHEN & THEN
        assertThrows<ResourceNotFoundException> {
            authService.login(request)
        }
        verify(exactly = 1) { userRepository.findByUsername(request.username) }
        verify(exactly = 0) { passwordEncoder.matches(any(), any()) }
        verify(exactly = 0) { jwtTokenProvider.generateToken(user) }
    }

    @Test
    fun `should throw exception when password is incorrect`() {
        // GIVEN
        val request = LoginRequestDTO("admin", "wrongPassword")
        val user = User(1L, "admin", "admin@example.com", "hashedPassword")

        every { userRepository.findByUsername(request.username) } returns user
        every { passwordEncoder.matches(request.password, user.password) } returns false

        // WHEN & THEN
        assertThrows<InvalidCredentialsException> {
            authService.login(request)
        }
        verify(exactly = 1) { userRepository.findByUsername(request.username) }
        verify(exactly = 1) { passwordEncoder.matches(request.password, user.password) }
        verify(exactly = 0) { jwtTokenProvider.generateToken(user) }
    }
}
