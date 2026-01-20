package com.ks.culinario.application.service

import com.ks.culinario.data.mapper.UserMapper
import com.ks.culinario.domain.model.User
import com.ks.culinario.domain.repository.UserRepository
import com.ks.culinario.infrastructure.service.UserServiceImpl
import com.ks.culinario.network.dto.UserDTO
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class UserServiceImplTest {

    private val userRepository: UserRepository = mockk()
    private val userMapper: UserMapper = mockk()
    private val userService = UserServiceImpl(userRepository, userMapper)

    @Test
    fun `should return UserDTO when user exists`() {
        // GIVEN
        val userId = 1L
        val user = User(userId, "testUser", "test@example.com", "password")
        val userDTO = UserDTO(userId, "testUser", "test@example.com")

        every { userRepository.findById(userId) } returns user
        every { userMapper.toDTO(user) } returns userDTO

        // WHEN
        val result = userService.getUser(userId)

        // THEN
        assertEquals(userDTO, result)
        verify(exactly = 1) { userRepository.findById(userId) }
    }

    @Test
    fun `should throw exception when user does not exist`() {
        // GIVEN
        val userId = 1L
        every { userRepository.findById(userId) } returns null

        // WHEN & THEN
        assertThrows<RuntimeException> {
            userService.getUser(userId)
        }
    }

    @Test
    fun `should return list of users`() {
        // GIVEN
        val user = User(1L, "user", "email", "pass")
        val userDTO = UserDTO(1L, "user", "email")

        every { userRepository.findAll() } returns listOf(user)
        every { userMapper.toDTO(user) } returns userDTO

        // WHEN
        val result = userService.getAllUsers()

        // THEN
        assertEquals(1, result.size)
        assertEquals(userDTO, result[0])
        verify(exactly = 1) { userRepository.findAll() }
    }

    @Test
    fun `should create user`() {
        // GIVEN
        val userDTO = UserDTO(null, "newUser", "new@email.com")
        val userDomain = User(null, "newUser", "new@email.com", "")
        val savedUser = User(1L, "newUser", "new@email.com", "")
        val savedUserDTO = UserDTO(1L, "newUser", "new@email.com")

        every { userMapper.toDomain(userDTO) } returns userDomain
        every { userRepository.save(userDomain) } returns savedUser
        every { userMapper.toDTO(savedUser) } returns savedUserDTO

        // WHEN
         userService.createUser(userDTO)

        // THEN
        verify(exactly = 1) { userRepository.save(userDomain) }
    }

    @Test
    fun `should update user`() {
        // GIVEN
        val userDTO = UserDTO(1L, "updatedUser", "updated@email.com")
        val userDomain = User(1L, "updatedUser", "updated@email.com", "")
        val updatedUser = User(1L, "updatedUser", "updated@email.com", "")
        val updatedUserDTO = UserDTO(1L, "updatedUser", "updated@email.com")

        every { userMapper.toDomain(userDTO) } returns userDomain
        every { userRepository.save(userDomain) } returns updatedUser
        every { userMapper.toDTO(updatedUser) } returns updatedUserDTO

        // WHEN
        userService.updateUser(userDTO)

        // THEN
        verify(exactly = 1) { userRepository.save(userDomain) }
    }

    @Test
    fun `should delete user`() {
        // GIVEN
        val userId = 1L
        every { userRepository.deleteById(userId) } just runs

        // WHEN
        userService.deleteUser(userId)

        // THEN
        verify(exactly = 1) { userRepository.deleteById(userId) }
    }
}
