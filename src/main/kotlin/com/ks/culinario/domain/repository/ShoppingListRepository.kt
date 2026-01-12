package com.ks.culinario.domain.repository

import com.ks.culinario.domain.model.ShoppingList

interface ShoppingListRepository {
    fun findAll(): List<ShoppingList>
    fun findById(id: Int): ShoppingList?
    fun save(shoppingList: ShoppingList): ShoppingList
    fun deleteById(id: Int)
}
