package com.ks.culinario.network.controller

import com.ks.culinario.domain.service.AuthService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(private val authService: AuthService) {

    @GetMapping("/login")
    fun login(): Boolean = authService.login()

    @GetMapping("/logout")
    fun logout(): Boolean = authService.logout()

    @GetMapping("/refresh")
    fun refresh(): Boolean = authService.refresh()
}
