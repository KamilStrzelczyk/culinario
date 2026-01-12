package com.ks.culinario.infrastructure.service

import com.ks.culinario.domain.model.User
import com.ks.culinario.domain.repository.UserRepository
import com.ks.culinario.domain.service.UserService
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userRepository: UserRepository) : UserService {

    override fun getAllUsers(): List<User> = userRepository.findAll()

    override fun getUser(id: Long): User {
        return userRepository.findById(id) ?: throw RuntimeException("User not found")
    }

    override fun createUser(user: User): User {
        return userRepository.save(user)
    }

    override fun deleteUser(id: Long) {
        userRepository.deleteById(id)
    }

    override fun updateUser(user: User): User {
        // Tutaj mogłaby być logika sprawdzająca czy user istnieje
        return userRepository.save(user)
    }
}
