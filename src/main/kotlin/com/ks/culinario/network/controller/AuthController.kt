package com.ks.culinario.network.controller

import LoginRequestDTO
import com.ks.culinario.domain.service.AuthService
import com.ks.culinario.network.dto.AuthResponseDTO
import com.ks.culinario.network.dto.RefreshTokenRequest
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequestDTO): AuthResponseDTO {
        return authService.login(request)
    }

    @PostMapping("/refresh")
    fun refresh(@RequestBody request: RefreshTokenRequest): AuthResponseDTO {
        return authService.refresh(request)
    }

    @PostMapping("/logout")
    fun logout(authentication: Authentication) {
        val username = authentication.name
        authService.logout(username)
    }
}
