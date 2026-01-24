package com.ks.culinario.application.service

import com.ks.culinario.data.mapper.UserMapper
import com.ks.culinario.domain.exception.ResourceNotFoundException
import com.ks.culinario.domain.model.User
import com.ks.culinario.domain.repository.UserRepository
import com.ks.culinario.infrastructure.service.UserServiceImpl
import com.ks.culinario.network.dto.NewUserDTO
import com.ks.culinario.network.dto.UserDTO
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.security.crypto.password.PasswordEncoder

class UserServiceImplTest {

    private val userRepository: UserRepository = mockk()
    private val userMapper: UserMapper = mockk()
    private val passwordEncoder: PasswordEncoder = mockk()
    private val userService = UserServiceImpl(userRepository, userMapper, passwordEncoder)

    @Test
    fun `should return UserDTO when user exists`() {
        // GIVEN
        val userId = 1L
        val user = User(userId, "testUser", "test@example.com", "password")
        val userDTO = UserDTO(userId, "test@example.com", "testUser")

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
        assertThrows<ResourceNotFoundException> {
            userService.getUser(userId)
        }
    }

    @Test
    fun `should return list of users`() {
        // GIVEN
        val user = User(1L, "user", "email", "pass")
        val userDTO = UserDTO(1L, "email", "user")
        
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
        val newUserDTO = NewUserDTO("password", "new@email.com", "newUser")
        val userDomain = User(null, "newUser", "new@email.com", "password")
        val userWithHashedPassword = userDomain.copy(password = "hashedPassword")
        val savedUser = User(1L, "newUser", "new@email.com", "hashedPassword")
        val savedUserDTO = UserDTO(1L, "new@email.com", "newUser")

        every { userMapper.toDomain(newUserDTO) } returns userDomain
        every { passwordEncoder.encode("password") } returns "hashedPassword"
        every { userRepository.save(userWithHashedPassword) } returns savedUser
        every { userMapper.toDTO(savedUser) } returns savedUserDTO

        // WHEN
        val result = userService.createUser(newUserDTO)

        // THEN
        verify(exactly = 1) { userRepository.save(userWithHashedPassword) }
        verify(exactly = 1) { passwordEncoder.encode("password") }
    }

    @Test
    fun `should update user`() {
        // GIVEN
        val userId = 1L
        val userDTO = UserDTO(userId, "updated@email.com", "updatedUser")
        
        val existingUser = User(userId, "oldUser", "old@email.com", "pass")
        val updatedDomain = existingUser.copy(username = "updatedUser", email = "updated@email.com")
        val savedUser = updatedDomain
        
        every { userRepository.findById(userId) } returns existingUser
        every { userRepository.save(updatedDomain) } returns savedUser
        every { userMapper.toDTO(savedUser) } returns userDTO

        // WHEN
        val result = userService.updateUser(userDTO)

        // THEN
        verify(exactly = 1) { userRepository.save(updatedDomain) }
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
