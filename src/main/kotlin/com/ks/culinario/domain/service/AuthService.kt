package com.ks.culinario.domain.service

import LoginRequestDTO
import com.ks.culinario.network.dto.AuthResponseDTO
import com.ks.culinario.network.dto.RefreshTokenRequest

interface AuthService {

    fun login(request: LoginRequestDTO): AuthResponseDTO
    fun logout(username: String)
    fun refresh(request: RefreshTokenRequest): AuthResponseDTO
}
