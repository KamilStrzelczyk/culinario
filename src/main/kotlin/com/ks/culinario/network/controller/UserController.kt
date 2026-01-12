package com.ks.culinario.network.controller

import com.ks.culinario.domain.model.User
import com.ks.culinario.domain.service.UserService
import com.ks.culinario.network.dto.UserDTO
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    @GetMapping
    fun getAllUsers(): List<UserDTO> {
        return userService.getAllUsers().map { it.toDTO() }
    }

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Long): UserDTO {
        return userService.getUser(id).toDTO()
    }

    @PostMapping
    fun createUser(@RequestBody userDTO: UserDTO): UserDTO {
        val user = userDTO.toDomain()
        val createdUser = userService.createUser(user)
        return createdUser.toDTO()
    }

    @PutMapping
    fun updateUser(@RequestBody userDTO: UserDTO): UserDTO {
        val user = userDTO.toDomain()
        val updatedUser = userService.updateUser(user)
        return updatedUser.toDTO()
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long) {
        userService.deleteUser(id)
    }

    private fun User.toDTO() = UserDTO(
        id = this.id,
        username = this.username,
        email = this.email
    )

    private fun UserDTO.toDomain() = User(
        id = this.id,
        username = this.username,
        email = this.email
    )
}
