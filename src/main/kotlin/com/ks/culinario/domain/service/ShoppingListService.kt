package com.ks.culinario.domain.service

import com.ks.culinario.domain.model.ShoppingList

interface ShoppingListService {
    fun getAll(): List<ShoppingList>
    fun get(id: Int): ShoppingList
    fun create(shoppingList: ShoppingList): ShoppingList
    fun delete(id: Int)
}
