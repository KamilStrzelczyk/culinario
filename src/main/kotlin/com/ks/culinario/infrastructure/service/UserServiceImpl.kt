package com.ks.culinario.infrastructure.service

import com.ks.culinario.data.mapper.UserMapper
import com.ks.culinario.domain.exception.ResourceNotFoundException
import com.ks.culinario.domain.repository.UserRepository
import com.ks.culinario.domain.service.UserService
import com.ks.culinario.network.dto.NewUserDTO
import com.ks.culinario.network.dto.UserDTO
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper,
    private val passwordEncoder: PasswordEncoder
) : UserService {

    override fun getAllUsers(): List<UserDTO> {
        return userRepository.findAll().map { userMapper.toDTO(it) }
    }

    override fun getUser(id: Long): UserDTO {
        val user = userRepository.findById(id) ?: throw ResourceNotFoundException("User not found with id: $id")
        return userMapper.toDTO(user)
    }

    override fun createUser(newUserDTO: NewUserDTO) {
        val userDomain = userMapper.toDomain(newUserDTO)
        val userWithHashedPassword = userDomain.copy(
            password = passwordEncoder.encode(userDomain.password)
        )
        userRepository.save(userWithHashedPassword)
    }

    override fun deleteUser(id: Long) {
        userRepository.deleteById(id)
    }

    override fun updateUser(userDTO: UserDTO) {
        val existingUser = userRepository.findById(userDTO.id) ?: throw ResourceNotFoundException("User not found")
        val updatedDomain = existingUser.copy(
            username = userDTO.name,
            email = userDTO.email
        )
        userRepository.save(updatedDomain)
    }
}
