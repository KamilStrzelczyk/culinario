package com.ks.culinario.domain.model

import kotlinx.serialization.Serializable

data class ShoppingList(
    val id: Int,
    val title: String,
    val description: String,
    val items: List<ShoppingListItem>
) {
    @Serializable
    data class ShoppingListItem(val name: String, val amount: Int)
}
