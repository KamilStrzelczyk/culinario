package com.ks.culinario.domain.serviceImpl

import com.ks.culinario.domain.service.UserService
import com.ks.culinario.infrastructure.repository.UserRepository
import com.ks.culinario.web.controller.UserDTO
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userRepository: UserRepository) : UserService {
    override fun getAllUsers(): List<UserDTO> =
        userRepository.findAll()

    override fun getUser(id: Long): UserDTO =
        userRepository.findById(id)

    override fun createUser(userDTO: UserDTO) {

        val user = UserDTO(
            id = 1,
            username = userDTO.username,
            email = userDTO.email,
        )
        userRepository.save(user)
    }

    override fun deleteUser(id: Long) = userRepository.deleteById(id)

    override fun updateUser(userDTO: UserDTO) {}
}