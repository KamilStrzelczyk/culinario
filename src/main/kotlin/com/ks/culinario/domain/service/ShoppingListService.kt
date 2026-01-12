package com.ks.culinario.domain.service

import com.ks.culinario.infrastructure.repository.UserRepository
import com.ks.culinario.web.controller.UserDTO
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

interface ShoppingListService {

    fun getAll(): List<String>

    fun getActive(): List<String>

    fun create(shoppingList: String)
}
