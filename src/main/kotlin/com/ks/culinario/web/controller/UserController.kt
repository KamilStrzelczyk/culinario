package com.ks.culinario.web.controller

import com.ks.culinario.domain.service.UserService
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

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Long): UserDTO = userService.getUser(id)

    @PutMapping
    fun updateUser(@RequestBody userDTO: UserDTO) = userService.updateUser(userDTO)

    @PostMapping
    fun createUser(@RequestBody userDTO: UserDTO) = userService.createUser(userDTO)

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long) = userService.deleteUser(id)
}

data class UserDTO(
    val id: Long?,
    val username: String,
    val email: String,
)