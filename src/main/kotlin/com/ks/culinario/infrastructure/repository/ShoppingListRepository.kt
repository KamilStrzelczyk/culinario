package com.ks.culinario.infrastructure.repository

interface ShoppingListRepository {
    fun getAll(): List<String>

    fun getActive(): List<String>

    fun create(shoppingList: String)
}