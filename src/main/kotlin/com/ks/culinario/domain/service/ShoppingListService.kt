package com.ks.culinario.domain.service

import com.ks.culinario.network.dto.ShoppingListDTO

interface ShoppingListService {
    fun getAll(): List<ShoppingListDTO>
    fun get(id: Int): ShoppingListDTO
    fun create(shoppingList: ShoppingListDTO)
    fun delete(id: Int)
}
