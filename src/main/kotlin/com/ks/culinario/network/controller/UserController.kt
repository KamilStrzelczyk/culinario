package com.ks.culinario.network.controller

import com.ks.culinario.domain.service.UserService
import com.ks.culinario.network.dto.UserDTO
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    @GetMapping
    fun getAllUsers(): List<UserDTO> {
        return userService.getAllUsers()
    }

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Long): UserDTO {
        return userService.getUser(id)
    }

    @PostMapping
    fun createUser(@RequestBody userDTO: UserDTO) {
        userService.createUser(userDTO)
    }

    @PutMapping
    fun updateUser(@RequestBody userDTO: UserDTO) {
        userService.updateUser(userDTO)
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long) {
        userService.deleteUser(id)
    }
}
