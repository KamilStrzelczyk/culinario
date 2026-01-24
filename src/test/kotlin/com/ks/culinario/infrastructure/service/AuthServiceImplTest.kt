package com.ks.culinario.infrastructure.service

import LoginRequestDTO
import com.ks.culinario.domain.exception.InvalidCredentialsException
import com.ks.culinario.domain.exception.ResourceNotFoundException
import com.ks.culinario.domain.model.Token
import com.ks.culinario.domain.model.User
import com.ks.culinario.domain.repository.TokenRepository
import com.ks.culinario.domain.repository.UserRepository
import com.ks.culinario.network.security.JwtTokenProvider
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.util.ReflectionTestUtils

class AuthServiceImplTest {

    private val userRepository: UserRepository = mockk()
    private val tokenRepository: TokenRepository = mockk()
    private val jwtTokenProvider: JwtTokenProvider = mockk()
    private val passwordEncoder: PasswordEncoder = mockk()
    
    // AuthServiceImpl ma teraz 4 zależności
    private val authService = AuthServiceImpl(userRepository, tokenRepository, jwtTokenProvider, passwordEncoder)

    init {
        // Wstrzykujemy wartości z @Value
        ReflectionTestUtils.setField(authService, "accessExpirationMs", "900000")
        ReflectionTestUtils.setField(authService, "refreshExpirationMs", "604800000")
    }

    @Test
    fun `should return token when login is successful`() {
        // GIVEN
        val request = LoginRequestDTO("admin", "admin123")
        val user = User(1L, "admin", "admin@example.com", "hashedPassword")
        val expectedToken = "jwt-token"

        every { userRepository.findByUsername(request.username) } returns user
        every { passwordEncoder.matches(request.password, user.password) } returns true
        every { tokenRepository.findAllByUser(user) } returns emptyList() // Brak starych tokenów
        every { jwtTokenProvider.generateToken(user) } returns expectedToken
        every { tokenRepository.save(any()) } returnsArgument 0 // Zwraca to, co dostał

        // WHEN
        val result = authService.login(request)

        // THEN
        assertEquals(expectedToken, result.accessToken)
        verify(exactly = 1) { userRepository.findByUsername(request.username) }
        verify(exactly = 1) { passwordEncoder.matches(request.password, user.password) }
        verify(exactly = 1) { jwtTokenProvider.generateToken(user) }
        verify(exactly = 2) { tokenRepository.save(any()) } // Access + Refresh
    }

    @Test
    fun `should throw exception when user is not found`() {
        // GIVEN
        val request = LoginRequestDTO("unknown", "password")
        every { userRepository.findByUsername(request.username) } returns null

        // WHEN & THEN
        assertThrows<ResourceNotFoundException> {
            authService.login(request)
        }
        verify(exactly = 1) { userRepository.findByUsername(request.username) }
        verify(exactly = 0) { passwordEncoder.matches(any(), any()) }
        verify(exactly = 0) { jwtTokenProvider.generateToken(any()) }
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
        verify(exactly = 0) { jwtTokenProvider.generateToken(any()) }
    }
}
