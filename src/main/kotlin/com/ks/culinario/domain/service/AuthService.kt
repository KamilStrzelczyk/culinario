package com.ks.culinario.domain.service

import LoginRequestDTO

interface AuthService {

    fun login(loginRequestDTO: LoginRequestDTO): String
    fun refresh(): Boolean
}
