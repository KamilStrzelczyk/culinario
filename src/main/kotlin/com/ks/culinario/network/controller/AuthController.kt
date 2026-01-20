package com.ks.culinario.network.controller

import LoginRequestDTO
import com.ks.culinario.domain.service.AuthService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequestDTO): String {
        return authService.login(loginRequestDTO = request)
    }

    @GetMapping("/logout")
    fun logout(): Boolean = authService.logout()

    @GetMapping("/refresh")
    fun refresh(): Boolean = authService.refresh()
}
