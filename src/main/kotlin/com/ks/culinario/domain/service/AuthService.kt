package com.ks.culinario.domain.service

import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping

interface AuthService {

    fun login(): Boolean
    fun logout(): Boolean
    fun refresh(): Boolean
}
