package com.ks.culinario.network.dto

data class ShoppingListDTO(
    val id: Int,
    val title: String,
    val description: String,
    val items: List<ShoppingListItemDTO>
)

data class NewShoppingListDTO(
    val title: String,
    val description: String,
    val items: List<ShoppingListItemDTO>
)

data class ShoppingListItemDTO(
    val name: String,
    val amount: Int
)
