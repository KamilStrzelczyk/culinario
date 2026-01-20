package com.ks.culinario.infrastructure.service

import com.ks.culinario.data.mapper.UserMapper
import com.ks.culinario.domain.exception.ResourceNotFoundException
import com.ks.culinario.domain.repository.UserRepository
import com.ks.culinario.domain.service.UserService
import com.ks.culinario.network.dto.UserDTO
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper
) : UserService {

    override fun getAllUsers(): List<UserDTO> {
        return userRepository.findAll().map { userMapper.toDTO(it) }
    }

    override fun getUser(id: Long): UserDTO {
        return userRepository.findById(id)?.let {
            userMapper.toDTO(it)
        } ?: throw ResourceNotFoundException("User not found with id: $id")
    }

    override fun createUser(user: UserDTO) {
        userRepository.save(userMapper.toDomain(user))
    }

    override fun deleteUser(id: Long) {
        userRepository.deleteById(id)
    }

    override fun updateUser(user: UserDTO) {
        userRepository.save(userMapper.toDomain(user))
    }
}
