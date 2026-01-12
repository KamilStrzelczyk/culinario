package com.ks.culinario.domain.service

interface AuthService {

    fun login(): Boolean
    fun logout(): Boolean
    fun refresh(): Boolean
}
